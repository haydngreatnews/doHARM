=============================
For floor tiles
=============================

tilesetname.png
tile width
tile height
tile_name (int type of tile 0=wall, 1=grass, 2=nothing, 3=water etc)  (integers indicating which images belong to this tile in the tileset.png) (tile animation speed, only needed if num tiles > 1)

NOTE: exclude brackets from the above line when writting the tileset.txt, they are just to keep things tidy. .


=============================
For wall tiles
=============================
tilesetname.png
tile x
tile y
tile_name (int: corresponding floor tile type) (2 int's, one for location of left wall sprite, and one for location in the tileset of the right wall sprite)
