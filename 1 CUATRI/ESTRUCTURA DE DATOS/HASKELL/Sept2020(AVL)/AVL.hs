{------------------------------------------------------------------------------
 - Student's name:
 -
 - Student's group:
 -----------------------------------------------------------------------------}

module AVL 
  ( 
    Weight
  , Capacity
  , AVL (..)
  , Bin
  , emptyBin
  , remainingCapacity
  , addObject
  , maxRemainingCapacity
  , height
  , nodeWithHeight
  , node
  , rotateLeft
  , addNewBin
  , addFirst
  , addAll
  , toList
  , linearBinPacking
  , seqToList
  , addAllFold
  ) where

type Capacity = Int
type Weight= Int

data Bin = B Capacity [Weight] 

data AVL = Empty | Node Bin Int Capacity AVL AVL deriving Show


emptyBin :: Capacity -> Bin
emptyBin c = B c []

remainingCapacity :: Bin -> Capacity
remainingCapacity (B c xs) = c

addObject :: Weight -> Bin -> Bin
addObject e (B c xs)
                | c-e>=0 = B (c-e) (xs ++ [e])
                | otherwise = error "Not enough space"

maxRemainingCapacity :: AVL -> Capacity
maxRemainingCapacity Empty = 0
maxRemainingCapacity (Node (B c xs) a cap l r) = cap

height :: AVL -> Int
height Empty = 0
height (Node (B c xs) a cap l r)= a


 
nodeWithHeight :: Bin -> Int -> AVL -> AVL -> AVL
nodeWithHeight b@(B c xs) a l r
                      | c >= cl && c >= cr  = Node b a c  l r
                      | cl >= c && cl >= cr = Node b a cl l r
                      | otherwise           = Node b a cr l r
                      where
                        cl = maxRemainingCapacity l
                        cr = maxRemainingCapacity r


node :: Bin -> AVL -> AVL -> AVL
node b l Empty = nodeWithHeight b (height l + 1) l Empty
node b Empty r = nodeWithHeight b (height r +1 ) Empty r
node b l@(Node bl al cl ll rl) r@(Node br ar cr lr rr)
                                          | al >= ar  = nodeWithHeight b (al+1) l r
                                          | otherwise = nodeWithHeight b (ar+1) l r


rotateLeft :: Bin -> AVL -> AVL -> AVL
rotateLeft _ _ Empty = error "Empty rigth AVL"
rotateLeft b l r@(Node br ar cr lr rr) = node br (node b l lr) rr
                                            

addNewBin :: Bin -> AVL -> AVL
addNewBin b@(B c xs) Empty = node b Empty Empty
addNewBin b (Node bt h ct lt Empty) = node bt lt (addNewBin b Empty)
addNewBin b1@(B c xs) (Node b a cap l r)
                        | abs(height l - height newr) >=1 = rotateLeft b l newr
                        | otherwise                    = node b l newr
                        where
                          newr = addNewBin b1 r
 
addFirst :: Capacity -> Weight -> AVL -> AVL
addFirst w el Empty = addNewBin (B (w-el) [el]) Empty
addFirst w el avl@(Node b@(B c xs) a cap l r)
                        | cap<el                       = addNewBin (B (w-el) [el]) avl
                        | maxRemainingCapacity l >= el = node b (addFirst w el l) r
                        | c >= el                      = node (addObject el b) l r
                        | otherwise                    = node b l (addFirst w el r)

addAll:: Capacity -> [Weight] -> AVL
addAll w xs = aux w xs Empty
          where
            aux _ [] a     = a 
            aux w (x:xs) a = aux w xs (addFirst w x a)

toList :: AVL -> [Bin]
toList Empty = []
toList (Node b a c l r) = toList l ++ [b] ++ toList r

{-
	SOLO PARA ALUMNOS SIN EVALUACION CONTINUA
  ONLY FOR STUDENTS WITHOUT CONTINUOUS ASSESSMENT
 -}

data Sequence = SEmpty | SNode Bin Sequence deriving Show  

linearBinPacking:: Capacity -> [Weight] -> Sequence
linearBinPacking _ _ = undefined

seqToList:: Sequence -> [Bin]
seqToList _ = undefined

addAllFold:: [Weight] -> Capacity -> AVL 
addAllFold _ _ = undefined



{- No modificar. Do not edit -}

objects :: Bin -> [Weight]
objects (B _ os) = reverse os

  
instance Show Bin where
  show b@(B c os) = "Bin("++show c++","++show (objects b)++")"