Author: Beatrix Klebe

This project presents the problem of keeping large amounts of iteration
clean and concise, as we are presented with three files of increasing
size and asked to process them into a graph representation. I chose an
adjacency list graph representation because an adjacency matrix requires
at least n^2 bits to represent a graph, and this is far too many for the
number of elements in the nys.txt file. External iteration is messy and
error prone, so this project leverages functional programming techniques
and the java.nio non-blocking IO package to scan all lines of the file
into a single data structure at once and then build each line into either
a Vertex or an Edge object. The code roughly follows the model-view-controller
pattern, where the Map program just loads the command line arguments
into a generic Collection and passes it off to Launcher, which is the
a bit like root controller.

This library is contained in a package called map and is separated into
the view (which loads the GUI), the model (which persists and operates on
the graph), and the controller (which mediates between the model and the
view). To run the project, run Map.java with the defined command line arguments.
Obviously -meridianmap is current unimplemented.

Status:
So far I have completed the first two parts of the project, and they
run fast, but I have yet to finish the minimum weight spanning tree
and the runtime analysis. The map scaling is inexplicably fucked on the UR campus dataset, I suspect because I was testing it on the NYS one most of the time. Monroe County could be better and for some reason the red path line isn't showing but none of these problems are stuff I want to dig into at 3am on code I haven't seriously touched since last April.