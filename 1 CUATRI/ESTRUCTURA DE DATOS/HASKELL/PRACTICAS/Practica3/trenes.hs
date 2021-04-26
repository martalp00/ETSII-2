-- =====================================================
-- Un tren está compuesto por una máquina y vagones
-- Los vagones pueden transportar bidones. 
-- Cada vagón puede transportar un máximo de bidones
-- El máximo lo define una función tope 
data Tren = Maquina | Vagon Int Tren 
ejemplo = Vagon 5 (Vagon 3 (Vagon 4 Maquina))

tope = 10

-- Determina cuántos bidones lleva el tren.
bidones :: Tren -> Int
bidones  Maquina = 0
bidones (Vagon b rt) = b + bidones rt

-- Carga n bidones a un tren.
-- Si caben en los vagones que hay se añaden a estos
-- si no caben, se añaden los vagones que hagan falta
carga :: Int -> Tren -> Tren
carga  0 t = t
carga n Maquina 
    | n < tope  = Vagon n Maquina
    | otherwise = Vagon tope (carga (n - tope) Maquina)
carga n (Vagon c rt)
    | n <= caben = Vagon (n + c) rt
    | otherwise  = Vagon tope (carga (n - caben) rt) 
    where 
        caben = tope - c

-- descarga n bidones del tren
-- si no hay suficientes da un error
-- si al descargar, un  vagon se queda sin carga, se desengancha del tren
descarga :: Int -> Tren -> Tren
descarga n Maquina | n > 0 = error "no hay tantos bidones"
descarga 0 Maquina = Maquina
descarga n (Vagon c rt)
    | n == c    = rt
    | n < c     = Vagon (c - n) rt
    | otherwise = descarga (n - c) rt 

-- optimiza el número de vagones del tren
-- No permite que mas de un vagon vaya semicargado
optimiza :: Tren -> Tren
optimiza Maquina = Maquina
optimiza (Vagon b t) 
    | b == tope = Vagon b (optimiza t)
    | otherwise = carga b (optimiza t) 

-- Une dos trenes en uno solo. Carga los contenidos de un tren en el otro
une:: Tren -> Tren -> Tren
une Maquina tren = tren
une (Vagon n t) tren = une t (carga n tren)

-- =====================================================
instance Show Tren where
    show Maquina = "XxIx>"
    show (Vagon c rt) = concat [show c, "-", show rt]