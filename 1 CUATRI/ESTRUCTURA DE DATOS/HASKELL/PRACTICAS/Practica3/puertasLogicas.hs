-- La expresión lógica
--          (~p) &&  (q || p && q)
-- Puede representarse como un árbol
--            &&
--          /   \
--         ~     ||
--         |    /  \
--         p   q   &&
--                 / \
--                p   q

data ExprL = And ExprL ExprL | 
             Not ExprL | 
             Or ExprL ExprL | 
             E Bool deriving Show

ejemplo:: Bool -> Bool -> ExprL
ejemplo p q = And (Not (E p)) (Or (E q) (And (E p) (E q)))
-- prelude> ejemplo True False
-- And (Not (E True)) (Or (E False) (And (E True) (E False)))

-- evalua una expresión 
evalua :: ExprL -> Bool 
evalua (E b) = b
evalua (And p1 p2) = evalua p1 && evalua p2
evalua (Or  p1 p2) = evalua p1 || evalua p2
evalua (Not p) = not (evalua p)
-- prelude> evalua (ejemplo True False)
-- False

-- Construye la tabla de verdad de la expresión ejemplo p q
tablaDeVerdad :: [(Bool,Bool,Bool)]
tablaDeVerdad = [(p, q, evalua (ejemplo p q)) | 
                                    let vls = [True, false]
                                    p <- vls, 
                                    q <- vls]
-- prelude> tabla de verdad
-- (True,True,False),(True,False,False),(False,True,True),(False,False,False)]