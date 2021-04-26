-- Una expresión son sumas, restas y multiplicaciones y se puede expresar 
-- como un árbol
--  x * (y + x * 2) - y
--            -
--          /   \
--         *     y
--        / \
--       x   +
--          / \  
--         y   *
--            / \ 
--           x   2

data Expr = Dato Int | Suma Expr Expr|  Resta Expr Expr| Multiplica Expr Expr deriving Show

-- x * (y + x * 2) - y
ejemplo :: Int -> Int -> Expr
ejemplo x y = Resta (Multiplica 
                        (Dato x) 
                        (Suma (Dato y) 
                              (Multiplica (Dato x) 
                                          (Dato 2)))) 
                    (Dato y)
-- prelude > ejemplo 3 4
-- Resta (Multiplica (Dato 3) (Suma (Dato 4) (Multiplica (Dato 3) (Dato 2)))) (Dato 4)

-- evalua una expresión devolviendo el valor de la expresión
evalua:: Expr  -> Int
evalua (Dato x)           = x
evalua (Suma e1 e2)       = evalua e1 + evalua e2
evalua (Resta e1 e2)      = evalua e1 - evalua e2
evalua (Multiplica e1 e2) = evalua e1 * evalua e2

-- prelude > evalua (ejemplo 3 4)
-- 26

-- Notación Polaca Inversa
-- Una notación para expresiones donde no se necesitan paréntesis
-- Un operador se introducen cuando ya se han introducido sus argumentos
-- la expresión:     x * (y + x * 2) - y
-- en NPI es:        x y x 2 * + * y -
data NPI = DNPI  Int | SNPI | RNPI | MNPI deriving  Show
type ExprNPI = [NPI]

-- Paso de una expresión a NPI
anpi:: Expr -> ExprNPI
anpi (Dato x) =  [DNPI x]
anpi (Suma e1 e2)        =   anpi e1 ++ anpi e2 ++ [SNPI]
anpi (Resta e1 e2)       =   anpi e1 ++ anpi e2 ++ [RNPI]
anpi (Multiplica e1 e2)  =   anpi e1 ++ anpi e2 ++ [MNPI]

-- prelude> anpi (ejemplo 3 4) 
-- [DNPI 3,DNPI 4,DNPI 3,DNPI 2,MNPI,SNPI,MNPI,DNPI 4,RNPI]

-- Evaluar una expresión en NPI
-- x y x 2 * + * y -  
-- Partimos de una lista vacía como acumulador
-- x y x 2 * + * y -  []
-- y x 2 * + * y -    [x]
-- x 2 * + * y -      [y,x]
-- 2 * + * y -        [x,y,x]
-- * + * y -          [2,x,y,x]
-- + * y -            [x*2,y,x]
-- * y -              [y+x*2,x]
-- y -                [x*(y+x*2)]
-- -                  [y,x*(y+x*2)]
--                    [x*(y+x*2)-y]
--                     x*(y+x*2)-y

evaluaENPI :: ExprNPI -> Int
evaluaENPI es = evaluaENPI' es []
    where
        evaluaENPI' []            [x]      = x
        evaluaENPI' ((DNPI x):xs) ss       = evaluaENPI' xs (x:ss)
        evaluaENPI' (SNPI:xs)     (y:x:ss) = evaluaENPI' xs  ((x+y):ss)
        evaluaENPI' (RNPI:xs)     (y:x:ss) = evaluaENPI' xs  ((x-y):ss)
        evaluaENPI' (MNPI:xs)     (y:x:ss) = evaluaENPI' xs  ((x*y):ss)
        evaluaENPI' _ _ = error "Expresión mal formada"

-- prelude> evaluaENPI (anpi (ejemplo 3 4))
-- 26