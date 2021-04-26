-------------------------------------------------------------------------------
-- Estructuras de Datos. 2º Curso. ETSI Informática. UMA
--
-- Titulación: Grado en Ingeniería del Software
-- Alumno: CAÑADAS COBO, ENRIQUE
-- Fecha de entrega: 28 | 10 | 2020
--
-- Relación de Ejercicios 2. Ejercicios resueltos: 24
--
-------------------------------------------------------------------------------
module CCE-Rel2-1-24 where
import Test.QuickCheck
import Data.List

-- Ejercicio 1

-- a

data Direction = North | South | East | West deriving (Eq, Ord, Enum, Show)

(<<) :: Direction -> Direction -> Bool
x << y = fromEnum x < fromEnum y

p_menor x y = (x<y) == (x<<y)
instance Arbitrary Direction where
    arbitrary = do
        n <- choose (0,3)
        return $ toEnum n

-- b

{-
data Direction' = North | South | East | West deriving (Eq, Enum, Show)

(<=) :: Direction' -> Direction' -> Bool

-}

-- Ejercicio 2
-- Desconocia que existia la funcion predefinida maximum, usandola se simplificaría todo

ordenacion :: Ord a => [a] -> [a]
ordenacion [] = []
ordenacion (x:xs) = 
    ordenacion menores ++ [x] ++ ordenacion mayores
    where menores = [y | y <- xs, y <= x]
          mayores = [y | y <- xs, y >  x]

-- a

máximoYresto :: Ord a => [a] -> (a,[a])
máximoYresto [] = error "Lista vacía"
máximoYresto [x] = (x, [])
máximoYresto xs = (head (reverse (ordenacion xs)), [ x | x <- xs , x /= maximo])
    where maximo = head (reverse (ordenacion xs))

-- b

máximoYresto' :: Ord a => [a] -> (a,[a])
máximoYresto' [] = error "Lista vacía"
máximoYresto' [x] = (x, [])
máximoYresto' xs = (head (reverse (ordenacion xs)), ordenacion([ x | x <- xs , x /= maximo]))
    where maximo = head (reverse (ordenacion xs))

-- Ejercicio 3

reparte :: [a] -> ([a], [a])
reparte [] = ([],[])
reparte [x] = ([x],[])
reparte (x:y:xs) = (x:lista1, y:lista2)
    where 
        (lista1, lista2) = reparte xs

-- Ejercicio 4

distintos :: Eq a => [a] -> Bool
distintos [] = True
distintos [x] = True
distintos (x:xs) = not(elem x xs) && distintos(xs)

-- Ejercicio 5

-- a

replicate' :: Int -> a -> [a]
replicate' n x = [x | n <- [1..n]]

-- b

p_replicate' n x = n >= 0 && n <= 1000 ==>
                            length (filter (==x) xs) == n
                            && length (filter (/=x) xs) == 0
                                where xs = replicate' n x

-- Ejercicio 6

divideA :: Integer -> Integer -> Bool
divideA x y = (mod y x) == 0

divisores :: Integer -> [Integer]
divisores n = [x | x <- [1..n], x `divideA` n]

divisores' :: Integer -> [Integer]
divisores' n = [x | x <- [(-n)..n], x/=0 && x `divideA` (abs(n))]

-- Ejercicio 7

-- a

mcd :: Integer -> Integer -> Integer
mcd x y
    | x<0 = mcd (-x) y
    | y<0 = mcd x (-y) 
    | x>y =  maximum([n | n <- [1..x], elem n (divisores x) && elem n (divisores' y)])
    | y>=x = maximum([n | n <- [1..y], elem n (divisores x) && elem n (divisores' y)])

-- b
-- Para x,y,z enteros, con x≠0, y≠0, z≠0, se verifica mcd(z*x, z*y) = |z| mcd(x,y)

p_mcd x y z = x>0 && y>0 && z>0 ==> (mcd (z*x) (z*y)) == abs(z) * (mcd x y)

-- c
-- mcd x y ∙ mcm x y = x ∙ y

mcm :: Integer -> Integer -> Integer
mcm x y = div (abs(x*y)) (mcd x y)

-- Ejercicio 8

-- a

esPrimo :: Integer -> Bool
esPrimo x = length(divisores x) == 2

-- b

primosHasta :: Integer -> [Integer]
primosHasta x = [n | n <- [1..x], esPrimo n]

-- c

primosHasta' :: Integer -> [Integer]
primosHasta' x = filter esPrimo [n | n <- [1..x]]

-- d

p1_primos x = primosHasta x == primosHasta' x

-- Ejercicio 9

-- a

pares :: Integer -> [(Integer, Integer)]
pares x
    | x>2 && mod x 2 == 0 = [(p1,p2) | p1 <- [1..x], p2 <- [1..x], p1<=p2 && esPrimo p1 && esPrimo p2 && (p1 + p2) == x]
    | otherwise = []

-- b

goldbach :: Integer -> Bool
goldbach n = not(null (pares n))

-- c

goldbachHasta :: Integer -> Bool
goldbachHasta n = and [goldbach x | x <- [2..n], x>2 && mod x 2 == 0]

-- d

goldbachDébilHasta :: Integer -> Bool
goldbachDébilHasta n
    | n>5 && mod n 2 /= 0 = and [goldbach (x-3) | x <- [7..n], mod x 2 /= 0]
    | otherwise = error "El numero tiene que ser impar mayor que 5"

-- Ejercicio 10

-- a

esPerfecto :: Integer -> Bool
esPerfecto x = sum(divisores x) - x == x

-- b

perfectosMenoresQue :: Integer -> [Integer]
perfectosMenoresQue n = [x | x <- [1..n], esPerfecto x]

-- Ejercicio 11

-- a

take' :: Int -> [a] -> [a]
take' n xs = [x | (p,x) <- zip [0..n-1] xs]

-- b

drop' :: Int -> [a] -> [a]
drop' n xs = [x | (p,x) <- zip [0..length xs] xs, p>=n]

-- c
--para cualquier n≥0 y cualquier lista xs,si concatenamos la lista take' n xs con la lista drop' n xs, obtenemos la lista original xs.

p1_takeDrop n xs = n>=0 ==> take' n xs ++ drop' n xs == xs

-- Ejercicio 12

-- a

concat' :: [[a]] -> [a]
concat' xss = foldr (++) [] xss

-- b

concat'' :: [[a]] -> [a]
concat'' xss = [y | x <- xss, y <- x]

-- Ejercicio 13

desconocida :: (Ord a) => [a] -> Bool
desconocida xs = and [x <= y | (x,y) <- zip xs (tail xs)]
-- El algoritmo comprueba si un array esta ordenado, para ello quita el primer elemento y compara los elementos uno a uno si x<=y.

-- Ejercicio 14

-- a

inserta :: (Ord a) => a -> [a] -> [a]
inserta x xs = takeWhile (<x) xs ++ [x] ++ dropWhile (<x) xs

-- b

inserta' :: (Ord a) => a -> [a] -> [a]
inserta' x xs = [n | n <- xs, n<x] ++ [x] ++ [n | n <-xs, x<=n]

-- c

p1_inserta x xs = desconocida xs ==> desconocida (inserta x xs)

-- d
-- Funciona ya que vamos insertando cada valor en su correspondiente posicion.

-- e

ordena :: (Ord a) => [a] -> [a]
ordena xs = foldr (inserta) [] xs

-- Ejercicio 15

-- a

geométrica :: Integer -> Integer -> [Integer]
geométrica x y = iterate (*y) x

-- b Que todos son multiplos de la razon

-- c

múltiplosDe :: Integer -> [Integer]
múltiplosDe x = iterate (+x) 0

-- d

potenciasDe :: Integer -> [Integer]
potenciasDe x = iterate (*x) 1

-- Ejercicio 16

-- a Ya definidica en el 15 c

-- b

primeroComún :: Ord a => [a] -> [a] -> a
primeroComún (x:xs) (y:ys)
    | x==y = x
    | x<y = primeroComún xs (y:ys)
    | x>y = primeroComún (x:xs) ys

-- c

mcm' :: Integer -> Integer -> Integer
mcm' x y = primeroComún (tail (múltiplosDe x)) (tail (múltiplosDe y))

-- d

p_mcm' x y = x >= y && y >= 0 ==> mcm' x y == lcm x y

-- Ejercicio 17

primeroComúnDeTres :: Ord a => [a] -> [a] -> [a] -> a
primeroComúnDeTres (x:xs) (y:ys) (z:zs)
    | x>y = primeroComúnDeTres (x:xs) ys (z:zs)
    | x<y = primeroComúnDeTres xs (y:ys) (z:zs)
    | y>z = primeroComúnDeTres (x:xs) (y:ys) zs
    | y<z = primeroComúnDeTres (x:xs) ys (z:zs)
    | x == y && x == z = x

-- Ejercicio 18

factPrimos :: Integer -> [Integer]
factPrimos x = fp x 2
    where 
        fp x divisor
            | cociente < divisor = [x]
            | resto == 0 = divisor : fp cociente divisor
            | otherwise = fp x (divisor + 1)
                where (cociente,resto) = divMod x divisor -- cociente y resto

-- c

factPrimos' :: Integer -> [Integer]
factPrimos' x = fp x 2
    where 
        fp x divisor
            | cociente < divisor = [x]
            | resto == 0 = divisor : fp cociente divisor
            | otherwise = if divisor == 2 then fp x (divisor + 1) else fp x (divisor + 2)
                where (cociente,resto) = divMod x divisor -- cociente y resto

-- d

p_factores x = product(factPrimos' x) == x


-- Ejercicio 19

-- a

mezcla :: [Integer] -> [Integer] -> [Integer]
mezcla [] [] = []
mezcla (x:xs) [] = x : mezcla xs []
mezcla [] (y:ys) = y : mezcla [] ys
mezcla (x:xs) (y:ys)
    | x == y = x : mezcla xs ys
    | x>y = y : mezcla (x:xs) ys
    | y>x = x : mezcla xs (y:ys)

-- b

mcm'' :: Integer -> Integer -> Integer
mcm'' x y = product (mezcla (factPrimos' x) (factPrimos' y))

-- c

p_mcm'' x y = x >= 0 && y >= 0 ==> mcm'' x y == lcm x y

-- Ejercicio 20

-- a

mezc' :: [Integer] -> [Integer] -> [Integer]
mezc' [] [] = []
mezc' (x:xs) [] = []
mezc' [] (y:ys) = []
mezc' (x:xs) (y:ys)
    | x == y = x : mezc' xs ys
    | x>y = mezc' (x:xs) ys
    | y>x = mezc' xs (y:ys)

-- b

mcd'' :: Integer -> Integer -> Integer
mcd'' x y = product (mezc' (factPrimos' x) (factPrimos' y))

-- c

p_mcd'' x y = x > 0 && y > 0 ==> mcd'' x y == gcd x y

-- Ejercicio 21

-- a

nub' :: (Eq a) => [a] -> [a]
nub' [] = []
nub' [x] = [x]
nub' (x:xs)
    | elem x (xs) = x : nub' (filter (/=x) xs)
    | otherwise = x : nub' xs

-- b

p_nub' xs = nub xs == nub' xs

-- c

p_sinRepes xs = distintos (nub' xs)

-- d

todosEn :: (Eq a) => [a] -> [a] -> Bool
todosEn xs ys = and([elem x ys | x <- xs])

p_sinRepes' xs = nub xs == nub' xs && todosEn xs (nub' xs)

-- Ejercicio 22

-- a

binarios :: Integer -> [[Char]]
binarios 0 = [[]]
binarios 1 = ["0", "1"]
binarios n = ["0" ++ x | x <- binarios(n-1)] ++ ["1" ++ x | x <- binarios(n-1)]

-- b

p_binarios n = n >= 0 && n <= 10 ==> long xss == 2^n && distintos xss && all(`todosEn` "01") xss
        where 
            xss = binarios n
            long :: [a] -> Integer
            long  xs = fromIntegral (length xs)

-- Ejercicio 23

-- a

varRep :: Integer -> [a] -> [[a]]
varRep 0 xs = [[]]
varRep n [] = [[]]
varRep n xs = [x:ys | x <- xs, ys <- varRep (n-1) xs]

-- b

p_varRep m xs = m > 0 && m <= 5 && distintos xs ==> long vss == n^m && distintos vss && all (`todosEn` xs) vss
    where 
        vss = varRep m xs
        n = long xs
        long :: [a] -> Integer
        long xs = fromIntegral (length xs)

-- Ejercicio 24

-- a

var :: Integer -> [Char] -> [[Char]]
var n [] = [[]]
var 0 xs = [[]]
var n xs = [x:ys | x <- xs, ys <- var (n-1) xs, not(elem x ys)]

-- b

p_var m xs = n <= 5 && distintos xs && m >= 0 && m <= n ==> long vss == fact n `div` fact (n-m) && distintos vss && all distintos vss && all (`todosEn` xs) vss
    where 
        vss = var m xs
        n = long xs
        fact :: Integer -> Integer
        fact x = product [1..x]
        long :: [a] -> Integer
        long xs = fromIntegral (length xs)

-- Ejercicio 25

intercala :: (Ord a) => a -> [a] -> [[a]]
intercala x [] = [[x]]

-- No me dio tiempo a terminarlo