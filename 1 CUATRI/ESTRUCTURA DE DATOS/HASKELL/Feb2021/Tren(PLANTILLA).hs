-------------------------------------------------------------------------------
-- Data Structures. February 2021.
-- Grado en Informática. UMA.
--
-- Student's name: Marta López Pérez
-- Student's group: 2ºA Ingeniería Informática
-------------------------------------------------------------------------------

module Tren where

-- =====================================================
-- Un tren está compuesto por una máquina y vagones
-- Los vagones pueden transportar objetos identififcados por su peso (Int).
-- Cada vagón puede transportar varios objetos hasta un peso máximo
-- (dado por la función tope)

import           Data.List

data Tren = Maquina | Vagon Int [Int] Tren

-- Maquina: un tren sin vagones
-- Vagon cr xs tren
--    cr es la capacidad restante del vagón (peso que aún cabe en el vagon)
--    xs es la lista de pesos que ya lleva el vagón
--    tren el resto del tren
-- Cuando se crea un vagón, el valor de la capacidad restante es tope y la
-- lista de pesos que ya lleva es vacía

-- Capacidad máxima de un vagón

tope :: Int
tope = 10 -- no se usa, se puede quitar

-- del: borra un objeto de un peso dado del tren. error si ese  peso no está en el tren
-- Encuentra el primer peso que coincida y lo quita del vagon.
-- Si como resultado de eliminar un peso, un vagón queda vacío de objetos
-- se desconecta del tren

-- =====================================================
-- Ejemplos de prueba

ejemplo1 :: Tren
ejemplo1 = Vagon 2 [5,3] (Vagon 3 [3,2,2] (Vagon 5 [5] Maquina))

ejemplo2 :: Tren
ejemplo2 = Vagon 1 [2,1,1,3,2] (Vagon 4 [4,2] Maquina)

-- |
-- >>> del 5 ejemplo1
-- (7,[3])-(3,[3,2,2])-(5,[5])-XxIx>
--
-- >>> del 5 (del 5 ejemplo1)
-- (7,[3])-(3,[3,2,2])-XxIx>
--
-- >>> foldr del ejemplo1 [5,2,5,3,3]
-- (8,[2])-XxIx>
--
-- >>> del 1 ejemplo2
-- (2,[2,1,3,2])-(4,[4,2])-XxIx>
--
-- >>> del 5 ejemplo2
-- (1,[2,1,1,3,2])-(4,[4,2])-*** Exception: No se ha encontrado ese peso en el tren

-- =====================================================

-- DO NOT MODIFY CODE ABOVE
-- data Tren = Maquina | Vagon Int [Int] Tren

del :: Int -> Tren -> Tren
del _ Maquina  = error("No hay tren") 
del x (Vagon cap list tren)
    | estaLista x list == True = Vagon (cap + x) (deleteLista x list) tren
    | otherwise = Vagon cap list (del x tren)


estaLista :: int -> [int] -> Bool 
estaLista _ [] = False 
estaLista x (a:xs)
    | x == a = True 
    | otherwise = estaLista x xs

deleteLista :: int -> [int] -> [int] 
deleteLista _ [] = []
deleteLista x (a:xs)
    | x == a = xs 
    | otherwise = a:deleteLista x xs

-- DO NOT MODIFY CODE BELOW

-- =====================================================

instance Show Tren where
    show Maquina         = "XxIx>"
    show (Vagon c xs rt) = concat ["(",show c,",",show xs,")","-",show rt]
