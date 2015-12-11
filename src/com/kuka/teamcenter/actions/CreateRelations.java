package com.kuka.teamcenter.actions;

import com.kuka.teamcenter.client.Session;
import com.teamcenter.schemas.soa._2006_03.exceptions.ServiceException;
import com.teamcenter.services.strong.cad.StructureManagementService;
import com.teamcenter.services.strong.cad._2007_01.DataManagement.*;
import com.teamcenter.services.strong.cad._2007_01.StructureManagement.RelOccInfo;
import com.teamcenter.services.strong.cad._2007_12.DataManagement.CreateOrUpdatePartsPref;
import com.teamcenter.services.strong.cad._2008_03.DataManagement.DatasetInfo3;
import com.teamcenter.services.strong.cad._2008_03.DataManagement.PartInfo3;
import com.teamcenter.services.strong.core.ReservationService;
import com.teamcenter.soa.client.model.ModelObject;
import com.teamcenter.soa.client.model.ServiceData;
import com.teamcenter.soa.client.model.strong.*;
import com.teamcenter.soa.exceptions.NotLoadedException;

import java.util.Calendar;

/**
 * Test Implementation to see what this does for me
 */
public class CreateRelations {


    private static final String BL_SEQUENCE_KEY="bl_sequence_no";
    private static final String ABS_OCC_ASSM_KEY ="AbsOccAssembly";
    private static final String ABS_OCC_ASSM_TITLE ="Abs Occ Assembly";
    private static final String PLATE_ASSEMBLEY_KEY ="PLATE_ASSEMBLEY_KEY";
    private static final String PLATE_ASSEMBLY_TITLE ="Plate Assembly";
    private static final String PLATE_KEY ="Plate";
    private static final String PLATE_TITLE = PLATE_KEY;
    private static final String Plate_DATASET_KEY ="PlateDataset";
    private static final String DIRECT_MODEL_KEY ="DirectModel";
    private static final String IMAN_RENDERING_KEY=             "IMAN_Rendering";
    private static final String PLATEHOLE_DATASET_KEY ="PlateHoleDataset";
    private static final String PLATEHOLE_DATASET_DESCRIPTION ="Plate with hole";

    private final com.teamcenter.services.strong.core.DataManagementService dmService;
    private final com.teamcenter.services.strong.cad.DataManagementService cadDmService;
    private final StructureManagementService structureService;
    private final ReservationService reservationService;

    private Folder homeFolder;

    private ItemRevision absOccAsmItemRev = null;
    private ItemRevision plateAsmItemRev = null;
    private ItemRevision plateItemRev = null;
    private Dataset plateDataset = null;
    private Dataset plateHoleDateset = null;

    public CreateRelations(User user) {

        dmService = com.teamcenter.services.strong.core.DataManagementService.getService(Session.getConnection());
        cadDmService = com.teamcenter.services.strong.cad.DataManagementService.getService(Session.getConnection());
        structureService = StructureManagementService.getService(Session.getConnection());
        reservationService = ReservationService.getService(Session.getConnection());

        try {
            homeFolder = user.get_home_folder();
        } catch (NotLoadedException e) {
            e.printStackTrace();
        }

    }

    public boolean createParts() {
        PartInfo3[] creInfos = new PartInfo3[3];

        creInfos[0] = new PartInfo3();
        creInfos[0].itemInput = new ItemInfo();
        creInfos[0].clientId = ABS_OCC_ASSM_KEY;
        creInfos[0].itemInput.name = ABS_OCC_ASSM_TITLE;
        creInfos[0].itemInput.folder = homeFolder;
        creInfos[0].itemRevInput = new ItemRevInfo();

        creInfos[1] = new PartInfo3();
        creInfos[1].itemInput = new ItemInfo();
        creInfos[1].clientId = PLATE_ASSEMBLEY_KEY;
        creInfos[1].itemInput.name = PLATE_ASSEMBLY_TITLE;
        creInfos[1].itemInput.folder = homeFolder;
        creInfos[1].itemRevInput = new ItemRevInfo();

        creInfos[2] = new PartInfo3();
        creInfos[2].itemInput = new ItemInfo();
        creInfos[2].clientId = PLATE_KEY;
        creInfos[2].itemInput.name = PLATE_TITLE;
        creInfos[2].itemInput.folder = homeFolder;
        creInfos[2].itemRevInput = new ItemRevInfo();

        creInfos[2].datasetInput = new DatasetInfo3[2];
        creInfos[2].datasetInput[0] = new DatasetInfo3();
        creInfos[2].datasetInput[0].clientId = Plate_DATASET_KEY;
        creInfos[2].datasetInput[0].name = PLATE_TITLE;
        creInfos[2].datasetInput[0].type = DIRECT_MODEL_KEY;
        creInfos[2].datasetInput[0].itemRevRelationName = IMAN_RENDERING_KEY;
        creInfos[2].datasetInput[0].description = PLATE_TITLE;
        creInfos[2].datasetInput[0].mapAttributesWithoutDataset =                false;
        creInfos[2].datasetInput[0].lastModifiedOfDataset =                Calendar.getInstance();

        creInfos[2].datasetInput[1] = new DatasetInfo3();
        creInfos[2].datasetInput[1].clientId = PLATEHOLE_DATASET_KEY;
        creInfos[2].datasetInput[1].name = PLATEHOLE_DATASET_DESCRIPTION;
        creInfos[2].datasetInput[1].type = DIRECT_MODEL_KEY;
        creInfos[2].datasetInput[1].itemRevRelationName = IMAN_RENDERING_KEY;
        creInfos[2].datasetInput[1].description = PLATEHOLE_DATASET_DESCRIPTION;
        creInfos[2].datasetInput[1].mapAttributesWithoutDataset =                false;
        creInfos[2].datasetInput[1].lastModifiedOfDataset =                Calendar.getInstance();

        CreateOrUpdatePartsResponse creResp =
                cadDmService.createOrUpdateParts(creInfos, new CreateOrUpdatePartsPref());

        if (creInfos.length == creResp.output.length) {
            for(int i=0;i<creResp.output.length;i++){
                CreateOrUpdatePartsOutput partsOut = creResp.output[i];

                switch (partsOut.clientId) {
                    case ABS_OCC_ASSM_KEY:
                        absOccAsmItemRev = partsOut.itemRev;
                        break;
                    case PLATE_ASSEMBLEY_KEY:
                        plateAsmItemRev = partsOut.itemRev;
                        break;
                    case PLATE_KEY:
                        plateItemRev = partsOut.itemRev;

                        for (DatasetOutput datasetOut : partsOut.datasetOutput) {
                            switch (datasetOut.clientId) {
                                case Plate_DATASET_KEY:
                                    plateDataset = datasetOut.dataset;
                                    break;
                                case PLATEHOLE_DATASET_KEY:
                                    plateHoleDateset = datasetOut.dataset;
                                    break;
                                default:
                                    return false;
                            }
                        }
                        break;

                    default:
                        return false;

                }
                return true;
            }
        }
            return false;

        }

    public void createStructure() {
        if (absOccAsmItemRev == null || plateAsmItemRev == null || plateItemRev == null || plateDataset == null || plateHoleDateset == null)
            return;

        try {

            StructureManagementService.CreateOrUpdateRelativeStructureInfo2[] relStructInfos = new StructureManagementService.CreateOrUpdateRelativeStructureInfo2[2];
            relStructInfos[0] = new StructureManagementService.CreateOrUpdateRelativeStructureInfo2();
            relStructInfos[0].parent = plateAsmItemRev;
            relStructInfos[0].precise = false;
            relStructInfos[0].childInfo = new StructureManagementService.RelativeStructureChildInfo[2];
            relStructInfos[0].childInfo[0] = new StructureManagementService.RelativeStructureChildInfo();
            relStructInfos[0].childInfo[0].child = plateItemRev;
            relStructInfos[0].childInfo[0].occInfo = new RelOccInfo();
            relStructInfos[0].childInfo[0].occInfo.attrsToSet = new StructureManagementService.AttributesInfo[1];
            relStructInfos[0].childInfo[0].occInfo.attrsToSet[0] = new StructureManagementService.AttributesInfo();

            relStructInfos[0].childInfo[0].occInfo.attrsToSet[0].name = BL_SEQUENCE_KEY;

            relStructInfos[0].childInfo[0].occInfo.attrsToSet[0].value = "10";
            relStructInfos[0].childInfo[0].occInfo.occTransform = new double[]{1, 0,
                    0, 0, 0, 1, 0, 0, 0, 0, 1, 0, 0, 0, 0, 1};
            relStructInfos[0].childInfo[1] = new StructureManagementService.RelativeStructureChildInfo();
            relStructInfos[0].childInfo[1].child = plateItemRev;
            relStructInfos[0].childInfo[1].occInfo = new RelOccInfo();
            relStructInfos[0].childInfo[1].occInfo.attrsToSet = new StructureManagementService.AttributesInfo[1];
            relStructInfos[0].childInfo[1].occInfo.attrsToSet[0] = new StructureManagementService.AttributesInfo();

            relStructInfos[0].childInfo[1].occInfo.attrsToSet[0].name = BL_SEQUENCE_KEY;

            relStructInfos[0].childInfo[1].occInfo.attrsToSet[0].value = "20";
            relStructInfos[0].childInfo[1].occInfo.occTransform = new double[]{1, 0, 0, 0, 0, 1, 0, 0, 0, 0, 1, 0, 0.11, 0, 0, 1};

            relStructInfos[1] = new StructureManagementService.CreateOrUpdateRelativeStructureInfo2();
            relStructInfos[1].parent = absOccAsmItemRev;
            relStructInfos[1].precise = false;
            relStructInfos[1].childInfo = new StructureManagementService.RelativeStructureChildInfo[1];
            relStructInfos[1].childInfo[0] = new StructureManagementService.RelativeStructureChildInfo();
            relStructInfos[1].childInfo[0].child = plateAsmItemRev;
            relStructInfos[1].childInfo[0].occInfo = new RelOccInfo();
            relStructInfos[1].childInfo[0].occInfo.attrsToSet = new StructureManagementService.AttributesInfo[1];
            relStructInfos[1].childInfo[0].occInfo.attrsToSet[0] = new StructureManagementService.AttributesInfo();

            relStructInfos[1].childInfo[0].occInfo.attrsToSet[0].name = BL_SEQUENCE_KEY;

            relStructInfos[1].childInfo[0].occInfo.attrsToSet[0].value = "10";
            relStructInfos[1].childInfo[0].occInfo.occTransform = new double[]{1, 0, 0, 0, 0, 1, 0, 0, 0, 0, 1, 0, 0, 0, 0, 1};

            StructureManagementService.CreateOrUpdateRelativeStructurePref2 relStructPref = new StructureManagementService.CreateOrUpdateRelativeStructurePref2();

            com.teamcenter.services.strong.cad._2007_01.StructureManagement.CreateOrUpdateRelativeStructureResponse relStructResp2 = structureService.createOrUpdateRelativeStructure(relStructInfos, "", true, relStructPref);

            if (checkOutAsmBVR()) {

                StructureManagementService.CreateOrUpdateAbsoluteStructurePref2 absStructPref = new StructureManagementService.CreateOrUpdateAbsoluteStructurePref2();
                absStructPref.cadOccIdAttrName = BL_SEQUENCE_KEY;


                StructureManagementService.CreateOrUpdateAbsoluteStructureInfo2[] absStructInfos = new StructureManagementService.CreateOrUpdateAbsoluteStructureInfo2[1];
                absStructInfos[0] = new StructureManagementService.CreateOrUpdateAbsoluteStructureInfo2();
                absStructInfos[0].contextItemRev = absOccAsmItemRev;
                absStructInfos[0].bvrAbsOccInfo = new StructureManagementService.AbsOccInfo[1];
                absStructInfos[0].bvrAbsOccInfo[0] = new StructureManagementService.AbsOccInfo();
                absStructInfos[0].bvrAbsOccInfo[0].cadOccIdPath = new String[]{"10", "10"};
                absStructInfos[0].bvrAbsOccInfo[0].absOccData = new StructureManagementService.AbsOccDataInfo();

                absStructInfos[0].bvrAbsOccInfo[0].absOccData.attachments = new StructureManagementService.AbsOccAttachment[1];

                absStructInfos[0].bvrAbsOccInfo[0].absOccData.attachments[0] = new StructureManagementService.AbsOccAttachment();

                absStructInfos[0].bvrAbsOccInfo[0].absOccData.attachments[0].dataset = plateDataset;

                absStructInfos[0].bvrAbsOccInfo[0].absOccData.attachments[0].relationTypeName = IMAN_RENDERING_KEY;

                absStructInfos[0].bvrAbsOccInfo[0].absOccData.occTransform = new double[]{1, 0, 0, 0, 0, 1, 0, 0, 0, 0, 1, 0, 0, 0.11, 0, 1};

                StructureManagementService.CreateOrUpdateAbsoluteStructureResponse absStructResp = structureService.createOrUpdateAbsoluteStructure(absStructInfos, "", true, absStructPref);


                StructureManagementService.CreateOrUpdateAbsoluteStructureInfo2[] absStructInfos2 = new StructureManagementService.CreateOrUpdateAbsoluteStructureInfo2[1];
                absStructInfos2[0] = new StructureManagementService.CreateOrUpdateAbsoluteStructureInfo2();
                absStructInfos2[0].contextItemRev = absOccAsmItemRev;
                absStructInfos2[0].bvrAbsOccInfo = new StructureManagementService.AbsOccInfo[1];
                absStructInfos2[0].bvrAbsOccInfo[0] = new StructureManagementService.AbsOccInfo();

                absStructInfos2[0].bvrAbsOccInfo[0].cadOccIdPath = new String[]{"10", "20"};
                absStructInfos2[0].bvrAbsOccInfo[0].absOccData = new StructureManagementService.AbsOccDataInfo();

                absStructInfos2[0].bvrAbsOccInfo[0].absOccData.attachments = new StructureManagementService.AbsOccAttachment[1];

                absStructInfos2[0].bvrAbsOccInfo[0].absOccData.attachments[0] = new StructureManagementService.AbsOccAttachment();

                absStructInfos2[0].bvrAbsOccInfo[0].absOccData.attachments[0].dataset = plateHoleDateset;

                absStructInfos2[0].bvrAbsOccInfo[0].absOccData.attachments[0].relationTypeName =IMAN_RENDERING_KEY;

                absStructInfos2[0].bvrAbsOccInfo[0].absOccData.occTransform = new double[]{1, 0, 0, 0, 0, 0, -1, 0, 0, 1, 0, 0, 0.11, 0, 0, 1};

                StructureManagementService.CreateOrUpdateAbsoluteStructureResponse absStructResp2 = structureService.createOrUpdateAbsoluteStructure(absStructInfos2, "", true, absStructPref);

                checkInAsmBVR();
            } else
            throw new RuntimeException("Error: Unable to check out absOccAsmItemRev");

        } catch (ServiceException e) {
            e.printStackTrace();
        }
    }

    private boolean checkOutAsmBVR() {
        dmService.getProperties(new ModelObject[]{absOccAsmItemRev}, new String[]{"structure_revisions"});

        try {
            PSBOMViewRevision[] absOccAsmBVRs = absOccAsmItemRev.get_structure_revisions();

            if (absOccAsmBVRs.length > 0) {
                ServiceData sd = reservationService.checkout(absOccAsmBVRs, "CheckOut for createOrUpdateAbsoluteStructure", "");

                if (sd.sizeOfUpdatedObjects() > 0)
                    return true;
            }
        } catch (NotLoadedException e) {
            e.printStackTrace();
        }

        return false;
    }

    private void checkInAsmBVR() {
        try {
            PSBOMViewRevision[] absOccAsmBVRs = absOccAsmItemRev.get_structure_revisions();

            if (absOccAsmBVRs.length > 0) {
                ServiceData sd = reservationService.checkin(absOccAsmBVRs);
            }
        } catch (NotLoadedException e) {
            e.printStackTrace();
        }
    }

}
