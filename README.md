# image-manipulation-program

## Project Title – Image Processing

MVC Structure
Our Assignment follows MVC architecture for processing images.
### Program Files
MODEL
- > ImageModel.java - Model for representing an image.
- > ImageModelImpl.java - Model implementation which represents an image in the form of
3D matrix.
- > ImageModelNew.java – New Representation of Image Model with added features.
- > ImageModelNewImpl.java - Model implementation which represents a new image in the
form of 3D matrix.
- > MockImage.java – Mock Model for Controller Testing.
- > ImageModelStore.java – A store that stores all Models used in the lifetime of program. It
performs save and load features so that the actual model (ImageModelNew) does not
change on new image formats. (Except png, jpg, ppm)
- > ImageModelStoreImpl.java – Implementation of ImageModelStore that stores all image
models being worked on.
- > ImageViewImpl.java – Implementation of the ImageGUI window and all the listeners
which delegates the processing tasks to controller callback methods.
CONTROLLER
- > ImageController.java - Controller Interface to control the flow of the program in a
specific way.
- > ImageControllerImpl.java - Controller implementation which uses switch case to
execute commands.
- > ControllerHelper.java - Helper Interface to store logical part of the controller.
- > ControllerHelperImpl.java - Implementation of the Interface.
- > ImageControllerGUIImpl.java – Controller for the GUI of the program.
- > Features.java – Interface implemented by New Controller
(ImageControllerGUIImpl.java) to add the feature callbacks used by the GUI View
(ImageGUIViewImpl.Java)
VIEW
- > ImageView.java - View Interface to provide command line user output.
- > ImageViewImpl.java - Implementation that message display method for command line.
- > ImageGUIView.java – Interface Implemented for creating the GUI view for the program.
- > ImageGUIViewImpl.java – GUI of the complete program which provides user to do
operations on the image.
RUN
- > Starter.java - To run the program.
### How to USE
- All the Detailed Instruction for How to Use the program in
various modes and their execution is described in USEME file.
### Modifications in GUI Design
• Added ImageModelStore.java Interface and its implementation which is basically the
main model used in the program which stores all the image models used.
(Justification – This was implemented to initialize an empty model store first and
pass it to the controller rather than controller initializing the Image model map
itself.)
• Added GUI Implementation files:
- ImageGUIView.java, ImageGUIViewImpl.java, ImageControllerGUIImpl.java,
Features.java
### Image Citation
• Image creator’s name - @Dharmil Shah
• Title of the image - boston.jpg , boston.png, boston.ppm, image-small.jpg,
image-small.png, image-small.ppm, boston-sky.png, boston-sky-small.png
• Date the image (or work represented by the image) was created - 1st
November 2023
• Date the image was posted online - 1st November 2023
• Date of access (the date you accessed the online image) - N/A
• Institution (gallery, museum) where the image is located/owned (if applicable)
- Personal (Northeastern University Student) © 2023
## Authors
• @Dharmil Shah
