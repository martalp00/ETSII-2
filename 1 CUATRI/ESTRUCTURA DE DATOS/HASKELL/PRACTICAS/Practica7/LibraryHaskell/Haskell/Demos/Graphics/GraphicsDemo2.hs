-------------------------------------------------------------------------------
-- Data Structures. Grado en Informática. UMA.
-- @ Pepe Gallardo, 2015
--
-- Some examples for vectorial graphics library
--
-------------------------------------------------------------------------------

module Demos.Graphics.GraphicsDemo where

import DataStructures.Graphics.Vectorial

import Prelude hiding (lines)

lines = sec [ ln [10,5]
            , Transformed [Translate 0 30] $ ln [5,5]
            , Transformed [Translate 0 60] $ ln [15, 10, 5, 10]
            , Transformed [Translate 0 90] $ ln [15, 10, 5]
            ]
 where ln ds = With [Thickness 5, Dotted ds] $ line (P (-200) 0) (P 200 0)


ellipses =
  sec $ zipWith elip [pi/6, 2*pi/6 .. 2*pi]
                     (cycle [red, green, blue, yellow])
 where
  elip alfa col =
     With [Transparency 0.65, Fill col, Brush col]
   . Transformed [Rotate alfa]
   $ ellipse (P 80 0) 40 30


arcs = sec [ ar 110 0 (pi/2), ar 0 0 pi, ar (-110) 0 (3*pi/2) ]
 where
        ar dy alfa beta = With [Thickness 3, Brush blue] $
                          arc (P 0 dy) 80 40 alfa beta


regularPoly n r = polygon [ P x y | i <- [0..n-1]
                                  , let alpha = 2*pi/fromIntegral n*fromIntegral i
                                  , let (x,y) = (r*cos alpha, r*sin alpha)
                                  ]

polygons = sec $    [ p l dx c | (l,dx,c) <- zip3 [3..6] [-70,0..] [red,green,blue,yellow] ]
                 ++ [ With [Brush magenta, Fill magenta, Font "Arial" 25] $ text (P 0 80) "Polygons !" ]
 where
  p l dx c = With [Thickness 3, Fill c] $
             Transformed [Translate dx 0] $ regularPoly l 30
