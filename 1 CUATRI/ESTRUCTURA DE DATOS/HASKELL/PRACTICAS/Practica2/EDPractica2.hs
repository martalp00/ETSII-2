-------------------------------------------------------------------------------
-- Estructuras de Datos. 2º Curso. ETSI Informática. UMA
--
-- PRACTICA 2ª (Características de la Programación Funcional)
--
-- (completa y sustituye los siguientes datos)
-- Titulación: Grado en Ingeniería Informática.
-- Alumno: LOPEZ PEREZ, MARTA
-- Fecha de entrega:  28 | OCTUBRE | 2020
--
-- Ejercicios resueltos de la Relación : ..........
--
-------------------------------------------------------------------------------
module Practica2 where

import Test.QuickCheck



-------------------------------------------------------------------------------
-- Ejercicio 4
-------------------------------------------------------------------------------
distintos :: Ord a => [a] -> Bool
distintos [] = True
distintos [x] = True
distintos [x,y] = x /= y
distintos (x:y:xs) = x /= y && distintos(x:xs) && distintos(y:xs)


-------------------------------------------------------------------------------
-- Ejercicio 10
-------------------------------------------------------------------------------
esPerfecto :: Integer -> Bool
esPerfecto 0 = False
esPerfecto 1 = False
esPerfecto x = aux 1 x
  where
    aux ac n | ac == n = True
    aux ac n
      | mod n 2 == 0      = aux (ac + 2) (div n 2)
      | mod n 3 == 0      = aux (ac + 3) (div n 3)
      | mod n 5 == 0      = aux (ac + 5) (div n 5)
      | otherwise         = False


perfectosMenoresQue :: Integer -> [a]
perfectosMenoresQue x = aux' 0 x []
  where
    aux' ac 0 res = res
    aux' ac n res
      | esPerfecto ac == True     = aux' (ac+1) (n-1) (res:ac)
      | otherwise                 = aux' (ac+1) (n-1) res


-------------------------------------------------------------------------------
-- Ejercicio 11
-------------------------------------------------------------------------------
-- take'
-- drop'

-------------------------------------------------------------------------------
-- Ejercicio 12
-------------------------------------------------------------------------------

-- concat
-------------------------------------------------------------------------------
-- Ejercicio 13
-------------------------------------------------------------------------------
desconocida :: (Ord a) => [a] -> Bool
desconocida xs = and [ x<=y | (x,y) <- zip xs (tail xs) ]
-- Qué hace?

-------------------------------------------------------------------------------
-- Ejercicio 14
-------------------------------------------------------------------------------
-- apartados a, b, e y f
-- a)
inserta :: (Ord a) => a -> [a] -> [a]
inserta x s = undefined


-- b)
inserta' :: (Ord a ) => a -> [a] -> [a]
inserta' x [] = undefined
inserta' x (y:ys) = undefined

-- e)

ordena :: (Ord a) => [a] -> [a]
ordena xs = undefined

-- f)  Utiliza para ello la función sorted definida en las transarencias



-------------------------------------------------------------------------------
-- Ejercicio 22
-------------------------------------------------------------------------------
binarios ::Integer -> [String]
binarios 0 = [""]
binarios x | x > 0 = undefined  

-------------------------------------------------------------------------------
-- Ejercicio 34
-------------------------------------------------------------------------------

type Izdo = Double
type Dcho = Double
type Epsilon = Double
type Función = Double -> Double
biparticion :: Función -> Izdo -> Dcho -> Epsilon -> Double

biparticion f a b epsilon
  | long < epsilon    = undefined
-- sigue aqui
  where
      long = b - a
-- sigue aqui


