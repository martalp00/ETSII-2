type Point  = (Double, Double) 
type Vector = (Double, Double) 
data Line = R Vector Point deriving Show
infix 4 ~=
(~=) :: Double -> Double -> Bool
x ~= y = abs(x-y) < 0.00000001

-- Distancia entre dos Points
distanceP2P :: Point -> Point -> Double
distanceP2P (x,y) (x',y') = sqrt ((x-x')^2 + (y-y')^2)

-- Vector que pasa a través de dos Points
vectorTPP:: Point -> Point -> Vector
vectorTPP (x,y) (x',y') =  (x-x', y-y')

-- Dice si dos vectores son paralelos
areParallelVectors :: Vector -> Vector -> Bool
areParallelVectors (x,y) (x',y') = x*y' ~= y*x'

-- Modulo de un vector
vectorModule :: Vector -> Double
vectorModule (u,v) = distanceP2P (u,v) (0,0)

-- Extremo de un vector si situa el origen en el Point dado
endPointFP :: Point -> Vector -> Point
endPointFP (x,y) (u,v) = (u+x,v+y)

-- vector ortogonal 
ortogonal :: Vector -> Vector
ortogonal (u,v) = (-v,u)

-- Line que pasa por dos Points
lineTPP :: Point -> Point -> Line  
lineTPP p1 p2 = R (vectorTPP p1 p2) p1

-- Line con vector director y Point
lineFVP :: Vector -> Point -> Line
lineFVP v p = R v p

-- vector director de una Line
director :: Line -> Vector
director (R v p) = v

-- Point por el que pasa una Line
pointInL :: Line -> Point
pointInL (R v p)  = p

-- Dice si la Line pasa por el Point dado
isLineThroughP :: Line -> Point -> Bool
isLineThroughP r p = areParallelVectors  (director r) (vectorTPP (pointInL r) p)

-- Dice si dos Lines son paralelas
areParallelLines :: Line -> Line ->Bool
areParallelLines r1 r2 = areParallelVectors (director r1) (director r2)

-- Calcula la Line paralela a la dada por el Point dado
parallelFP :: Line -> Point -> Line
parallelFP r p = lineFVP (director r) p

-- Calcula la Line perpendicular a la dada por el Point dado
perpendicularFP :: Line -> Point -> Line
perpendicularFP r p = lineFVP (ortogonal (director r)) p

-- Devuelve los coeficientes de 
-- la Forma implicita de una Line 
-- a x + b y + c = 0 ===> (a,b,c)
implicit :: Line -> (Double, Double, Double)
implicit r = (v,-u,y0*u-x0*v)
    where   
        (u ,v)  = director r
        (x0,y0) = pointInL r

-- Calcula el determinante 
--    |x y|
--    |z t|
determinant :: Double -> Double -> Double -> Double -> Double
determinant x y z t = x*t-y*z

-- Calcula la intersección de dos Lines
-- a x + b y + c  = 0
-- a'x + b'y + c' = 0
-- aplicando la regla de Cramer
--
--- det = |a  b |       detx =  |-c  b |       dety = |a  -c | 
--        |a' b'|               |-c' b'|              |a' -c'|
--
--       detx                  dety
-- x = ---------         y = ----------
--        det                   det
intersectionWith :: Line -> Line -> Maybe Point
intersectionWith r1 r2
    | areParallelLines r1 r2 = Nothing
    | otherwise = Just (detx/den,dety/den)
        where 
            (a, b, c)  = implicit r1
            (a',b',c') = implicit r2
            den  = determinant a b a' b'
            detx = determinant (-c) b (-c') b'
            dety = determinant a (-c) a' (-c')

distanceP2L :: Point -> Line -> Double
distanceP2L p r = distanceP2P p p'
    where
        v' = ortogonal (director r)
        r' = lineFVP v' p
        Just p' = intersectionWith r r'              

-- Calcula el area de un triángulo dado por tres Points
areaTriangle :: Point -> Point -> Point -> Maybe Double
areaTriangle p p' p'' | d > 0 && d' > 0 = Just (d*d'/2)
    where
        d = distanceP2P  p  p'
        r = lineTPP p p'
        d'= distanceP2L p'' r
areaTriangle _ _ _ = Nothing

-- Determina cuando dos rectas son iguales
instance Eq Line where
    R v p == R v' p' = areParallelVectors v v' && isLineThroughP (R v p) p'