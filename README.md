# Project Gärtner

## Motivation
Create useful UML diagrams for project documentation and ensure they are in sync with the actual Code

## Details
There is this excellent Project over at http://plantuml.com/

With plantuml one is able to write a simple description of a diagram and let plantuml do the layout and actual generation of an diagram image.

But for example if you want to create a Class diagram you have to repeat most of information already written in the java class for getting the diagram! There is the classname, list of fields, list of methods, relations to other classes. Repetition after repetition...

Woudn't it be nice if you could just tell a class that it should be part of a specific Diagram?

Yeah, so was my thougt too. And therefore here is Project Gärtner to address this.

Using annotation processing the annotations defined in gaertner-annotations are processed and diagrams are generated in compile time

## Name origin
OK, we want to simplify the generation of PlantUML Diagrams from Java Code? Yeah I know the team over at PlantUML had a factory plant in mind choosing this name. But a Plant can also be a synonym for flower. And who cares for Flowers? A gardener cares for flowers. Now in german we call a gardener "Gärtner". This is why this Project got its name.

