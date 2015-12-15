> # [Teamcenter](http://10.10.11.110/TeamCenter)

As a simulation engineer working in eMServer I will need to be able to export a system BOM from eMS and load it into Kuka's Teamcenter for releasing to purchasing.

#Solution
Custom application that extracts information from Tecnomatix product and imports into Teamcenter environment.


#Overview
This process can be broken up into two major tasks.

#Command
The Import/Export activity can only be loaded once all resources are loaded into Process Designer.

##Tecnomatix Export

	The data may be extracted from Tecnomatix using either the built in export eBop command or by using the TeamCenter BomView Command.
	
 - eBop
	 - This command will export the data into an Xml File.

 - TeamCenter Import
	 - This is a one step Export/Import Command that will export all of the selected items from Tecnomatix and import them into TeamCenter.


# *Export*
    There are several ways this may be accomplished.

  * DataTable
  The data may be exported to a datatable and saved into an excel format. My current difficulties with this method is the amount of data that is exported, as well as the configuration and user interaction required will allow the ability to have inconsistant data between projects and datasets. 
* Button
 I can parse through the code to retrieve the data from the application and recreate the dataset. By using this method and creating the same dataset as the XML, this would provide two different methods that this could take place with the same end results. This method would prove to be quicker than the xml method only because the XML data has to be exported to a file and then reimported. I would like to look into this later .

# *Process*
 
# *Import*


## Installation

TODO: Describe the installation process

## Usage

TODO: Write usage instructions

## Contributing

1. Fork it!
2. Create your feature branch: `git checkout -b my-new-feature`
3. Commit your changes: `git commit -am 'Add some feature'`
4. Push to the branch: `git push origin my-new-feature`
5. Submit a pull request :D

## History

TODO: Write history

## Credits

TODO: Write credits

## License

TODO: Write license
