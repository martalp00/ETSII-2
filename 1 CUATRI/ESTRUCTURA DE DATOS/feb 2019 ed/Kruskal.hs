----------------------------------------------
-- Estructuras de Datos.  2018/19
-- 2º Curso del Grado en Ingeniería [Informática | del Software | de Computadores].
-- Escuela Técnica Superior de Ingeniería en Informática. UMA
--
-- Examen 4 de febrero de 2019
--
-- ALUMNO/NAME:
-- GRADO/STUDIES:
-- NÚM. MÁQUINA/MACHINE NUMBER:
--
----------------------------------------------

module Kruskal(kruskal, kruskals) where

import qualified DataStructures.Dictionary.AVLDictionary as D
import qualified DataStructures.PriorityQueue.LinearPriorityQueue as Q
import DataStructures.Graph.DictionaryWeightedGraph

kruskal :: (Ord a, Ord w) => WeightedGraph a w -> [WeightedEdge a w]
kruskal = undefined

-- Solo para evaluación continua / only for part time students
kruskals :: (Ord a, Ord w) => WeightedGraph a w -> [[WeightedEdge a w]]
kruskals = undefined





 kruskal :: (Ord a, Ord w) => WeightedGraph a w -> [WeightedEdge a w]
kruskal g = aux pq0 dict0 []
where
pq0 = foldr (Q.enqueue) Q.empty (edges g)
dict0 = foldr (\v d -> D.insert v v d) D.empty (vertices g)

aux pq dict forest
	| Q.isEmpty pq = forest
	| r1 == r2 = aux (Q.dequeue pq) dict forest
	| otherwise = aux (Q.dequeue pq) (D.insert r2 v1 dict) (we:forest)
where
	r1 = representante v1 dict
	r2 = representante v2 dict
	we@(WE v1 p v2) = Q.first pq

representante::(Ord a) => a -> D.Dictionary a a -> a
representante v dict
	| w == v = v
	| w /= v = representante w dict
where
	Just w = D.valueOf v dict
