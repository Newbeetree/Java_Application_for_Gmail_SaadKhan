HOW TO USE MY PROGRAM
 On launch, the application will display either the configuration page or the main paige depending on
 if the configurations file exists in root. All fields must be present and must be correct in order to
 continue. Each stage has a menu bar which contains help and language tabs, the language tabs
 only contain french and english at the moment. switchign between stages causes the language to reset
 to default local which is english but the language can always be changed right afterwards. To display
 the readME go to help -> about, my text is dispalyed in an alert error box as a design choice to view
 whole of the readme file click display hidden details. Another small error is that for sending emails
 the format is important:
 
- name <emailaddress>, : the space and arrow keys are necessay, an example is displayed by default
- name <emailaddress>, name <emailaddress>, : all lists of emails must end with a comma 
- Drag and Drop is implemented in the main GUI
- emmbed is working perfectly although I reccommend not to send large images as they are saved in
 the temp and lack and will begin to take up space and slow down the DB
- refresh is handled every window recreate the button is there in case user wants to refresh right away
- delete will move emails to the delete folder, any emails deleted there will be deleted permenantly

