--------------------------------------------------------------------------------
-- Pepe Gallardo
--------------------------------------------------------------------------------

import Data.List(zip4)
import DataStructures.Graphics.Vectorial

regularPolyg :: Int -> Double -> Graphics
regularPolyg nSides r = polygon [ P x y | n <- [0..nSides-1]
                                        , let beta = alpha * fromIntegral n
                                        , let x = r * sin beta
                                        , let y = r * cos beta
                                        ]
 where
  alpha = 2*pi / fromIntegral nSides


spiral :: Graphics
spiral = sec [ With [Fill col] .
               Transformed [ Scale sc sc, Rotate alpha, Translate dx 0 ] $
               regularPolyg 5 10
             | (dx,alpha,sc,col) <- zip4 [ 10, 10.25 .. 500] -- desplazamientos eje X
                                         [ 0, pi/32 .. ]     -- giros
                                         [ 1, 1.01 .. ]      -- escalados
                                         (cycle [red,green,blue,yellow]) -- colores
             ]

demo1 = render spiral

--------------------------------------------------------------------------------

triangle :: Double -> Graphics
triangle l = polygon [P (-r) (-h'), P r (-h'), P 0 h' ]
 where
  r = l/2 -- mitad del lado
  h = l * sqrt 3 / 2 -- altura del triángulo
  h' = h/2

sierpinski :: Int -> Color -> Color -> Graphics
sierpinski 0 _  _          = nil
sierpinski n c1 c2 | n > 0 =
 (With [ Fill c1, Brush c1] $ triangle l) :>
 sec [ Transformed [ Translate x y, Scale 0.5 0.5 ] (sierpinski (n-1) c2 c1)
     | (x,y) <- zip [ -l/4, l/4, 0 ] [ -h/4, -h/4, h/4 ]
     ]
  where
   l = 500 -- lado del triángulo exterior
   h = l * sqrt 3 / 2  -- altura del triángulo exterior


demo2 = render (sierpinski 8 white blue)

--------------------------------------------------------------------------------

tree :: Int -> Graphics
tree n = Transformed [ Translate 0 (-100), Scale 2 2 ] $ aux n colors
 where
   aux 0 _              = nil
   aux n (c:cs) | n > 0 =
     With [ Thickness 10, Brush c ] $
       sec [ Line (P 0 100)
           , branch 40 (pi/3) 0.5
           , branch 60 (-7*pi/36) 0.76
           , branch 55 (-pi/4) 0.33
           ]
    where
     branch t g e = Transformed [ Translate 0 t, Rotate g, Scale e e ] $ aux (n-1) cs

colors :: [Color]
colors = drop 3 $ cycle [ rgb r g b | (r,g,b) <- cs ]
 where
  cs = [ (1-x,x,0) | x <- ss ]
    ++ [ (0,1-x,x) | x <- ss ]
    ++ [ (x,0,1-x) | x <- ss ]
  ss = [0,0.08..1]


demo3 = render (tree 9)
