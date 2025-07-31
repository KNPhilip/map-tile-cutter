# Map Tile Cutter
Simple tool for cutting a map/image out into tiles written in Clojure.

I was working on a project where I needed a way to cut out and nicely organize
a big picture into many smaller tiles. I decided to write this program to help me do that.

### Prerequisites
Make sure you have at least Java 11 installed in order to run the program.

### Installation

There are two primary ways to get going with the tool.

#### Run the latest release
1. Go to GitHub Releases and find the latest release
2. Download ```map-tile-cutter-0.1.0-SNAPSHOT-standalone.jar```
3. Run ```java -jar map-tile-cutter-0.1.0-SNAPSHOT-standalone.jar```

#### Run project with Leiningen
1. Install Leiningen
2. Clone the repository
3. Go to the project root and run ```lein run```

### How to use

When you have started the application you will see this window:
!["Preview  "](https://github.com/KNPhilip/map-tile-cutter/blob/main/docs/preview.jpg)

Let's take an example. I have a picture of a square, which I want to cut out into tiles.
In this case my picture is a perfect square (i.e _width = height_).

Since the system works in squared tiles,
your picture will have to be perfectly squared. Otherwise, the system will rescale your image for you and 
fill out the rest with the background color input.

Original Picture:

!["Original  "](https://github.com/KNPhilip/map-tile-cutter/blob/main/docs/square.jpg)

Exported Tiles:

!["Export  "](https://github.com/KNPhilip/map-tile-cutter/blob/main/docs/exported-tiles.jpg)

The format z/x_y means:
- _z = Number of cuts_
- _x = Vertical / Column_
- _y = Horizontal / Row_

Therefore, we see a folder **2** with **16** tiles in it, where *0_0* is the upper left corner.
*1_0* is the tile right to that one, which means *0_1* and *1_1* are respectively the tiles below them.

### Tools

Thanks to the following tools, I had an auspicious experience working on this project.

* Language: [Clojure](https://clojure.org/)
* Builder: [Leiningen](https://leiningen.org/)
* GUI: [SeeSaw](https://github.com/clj-commons/seesaw) / [Swing](https://docs.oracle.com/javase/tutorial/uiswing/)
* Testing: [speclj](https://github.com/slagyr/speclj)
