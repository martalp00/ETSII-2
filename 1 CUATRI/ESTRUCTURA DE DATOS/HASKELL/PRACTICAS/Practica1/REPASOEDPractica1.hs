-------------------------------------------------------------------------------
-- Estructuras de Datos. 2&ordm; Curso. ETSI Inform&aacute;tica. UMA
--
-- (completa y sustituye los siguientes datos)
-- Titulaci&oacute;n: Grado en Ingenier&iacute;a &hellip;&hellip;&hellip;&hellip;&hellip;&hellip;&hellip;&hellip;&hellip;&hellip;&hellip;&hellip;&hellip;&hellip; [Inform&aacute;tica | del Software | de Computadores].
-- Alumno: APELLIDOS, NOMBRE
-- Fecha de entrega:  DIA | MES | A&Ntilde;O
--
-- Relaci&oacute;n de Ejercicios 1. Ejercicios resueltos: .......... 
-- 
-------------------------------------------------------------------------------
import Test.QuickCheck

-------------------------------------------------------------------------------
-- Ejercicio 5
-------------------------------------------------------------------------------


entre :: Ord a => a -> (a, a) -> Bool
entre x (p,q)
               | x>=p && x<=q = True 
               | otherwise = False 


-------------------------------------------------------------------------------
-- Ejercicio 7
-------------------------------------------------------------------------------

-- Para este ejercicio nos interesa utilizar la función predefinida en Prelude: 
--              divMod :: Integral a => a -> a -> (a, a)
-- que calcula simultáneamente el cociente y el resto:
--
--   *Main> divMod 30 7
--   (4,2)

type TotalSegundos = Integer
type Horas         = Integer
type Minutos       = Integer
type Segundos      = Integer


descomponer :: TotalSegundos -> (Horas, Minutos, Segundos)
descomponer x = (horas, minutos, segundos)
   where
     (horas,resto)      = divMod x 3600
     (minutos, segundos) = divMod resto 60
     
     
p_descomponer x = x>=0 ==> h*3600 + m*60 + s == x
                           && m `entre` (0,59)
                           && s `entre` (0,59)
   where (h,m,s) = descomponer x

-- *Main&gt; quickCheck p_descomponer
-- +++ OK, passed 100 tests.

   
         
-------------------------------------------------------------------------------
-- Ejercicio 10
-------------------------------------------------------------------------------
         

-- Usaremos el operador ~= visto en clase (Tema 1, transparencia 31)
infix 0 ~=
(~=) :: (Ord a, Fractional a) => a -> a -> Bool
x ~= y = abs (x-y) < epsilon
  where epsilon = 1e-5



-- Primera soluci&oacute;n
-- no consideramos el estudio de las raices para a=0, 
raíces :: (Ord a, Floating a) => a -> a -> a -> (a, a)
raíces a b c 
  | dis < 0     = error "Raíces no reales"
  | otherwise   = (sol1, sol2)
 where  
         dis     = b^2 - 4*a*c
         sol1    = (-b + sqrt dis) / 2*a
         sol2    = (-b - sqrt dis) / 2*a

p1_raíces a b c  = True   ==> esRaíz r1 && esRaíz r2
-- atención, en el caso de True, podemos eliminar:  True ==> 
  where
   (r1,r2) = raíces a b c
   esRaíz r = a*r^2 + b*r + c ~= 0
 
p2_raíces a b c  = (a/=0)  && (dis>=0)   ==> esRaíz r1 && esRaíz r2
  where
   (r1,r2) = raíces a b c
   esRaíz r = a*r^2 + b*r + c ~= 0
   dis     = b^2 - 4*a*c



-------------------------------------------------------------------------------
-- Ejercicio 14
-------------------------------------------------------------------------------



-- potencia con base un n&uacute;mero arbitrario
potencia :: (Num b, Integral n) => b -> n -> b
potencia b 0 = 1
potencia b 1 = b
potencia b n = b*potencia b (n-1)


potencia' :: (Num b, Integral n) => b -> n -> b
potencia' b 0 = 1
potencia' b 1 = b
potencia' b 2 = b*b
potencia' b n 
            | mod n 2 == 0 = par
            | otherwise = impar
   where
      par = potencia' (potencia' b (div n 2)) 2
      impar = b * potencia' (potencia' b (div (n-1) 2)) 2


-- con esta propiedad (BASE un entero) no hay problemas
p_pot :: Integer -> Integer -> Property
p_pot b n = n>=0 ==> potencia b n == sol && potencia' b n == sol
 where sol = b^n

-- *Main&gt; quickCheck p_pot
-- +++ OK, passed 100 tests.



{- 

-- SEGUNDA OPCION: si consideramos una base arbitraria hay muchos problemas
p_pot&#039; :: (Ord b, Fractional b, Integral n) =&gt; b -&gt; n -&gt; Property
p_pot&#039; b n  = n&gt;=0 ==&gt; (potencia b n ~= sol) &amp;&amp; (potencia&#039; b n ~= sol)
   where sol = b^n
-- *Main&gt; quickCheck p_pot&#039;
-- *** Failed! Falsifiable (after 7 tests and 1 shrink):  
-- 4.374147831506856
-- 4   

-- Main&gt; potencia 850.1 5 - 850.1^5
-- 6.25e-2

-- Debemos ~= por un concepto de error relativo

-}


-------------------------------------------------------------------------------
-- Ejercicio 17
-------------------------------------------------------------------------------


mediana :: Ord a => (a, a, a, a, a) ->a
mediana (x,y,z,t,u) 
 | x > z = mediana (z,y,x,t,u)
 | y > z = mediana (x,z,y,t,u)
 | t < z = mediana (x,y,t,z,u)
 | u < z = mediana (x,y,t,u,z)
 |otherwise = z