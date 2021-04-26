(define (problem pb0)
  (:domain rush)
  (:requirements :strips :negative-preconditions)
  (:objects p0 p1 p2 p3 p4 p5 n x )
  (:init
    (near p0 p1)
    (near p1 p0)
    (near p1 p2)
    (near p2 p1)
    (near p2 p3)
    (near p3 p2)
    (near p3 p4)
    (near p4 p3)
    (near p4 p5)
    (near p5 p4)
    (equal p0 p0)
    (equal p1 p1)
    (equal p2 p2)
    (equal p3 p3)
    (equal p4 p4)
    (equal p5 p5)
    (empty n)
    (car x)   (horizontal x p2)
    (pos p0 p0 n) (pos p1 p0 n) (pos p2 p0 n) (pos p3 p0 n) (pos p4 p0 n) (pos p5 p0 n)
    (pos p0 p1 n) (pos p1 p1 n) (pos p2 p1 n) (pos p3 p1 n) (pos p4 p1 n) (pos p5 p1 n)
    (pos p0 p2 n) (pos p1 p2 n) (pos p2 p2 n) (pos p3 p2 x) (pos p4 p2 x) (pos p5 p2 n)
    (pos p0 p3 n) (pos p1 p3 n) (pos p2 p3 n) (pos p3 p3 n) (pos p4 p3 n) (pos p5 p3 n)
    (pos p0 p4 n) (pos p1 p4 n) (pos p2 p4 n) (pos p3 p4 n) (pos p4 p4 n) (pos p5 p4 n)
    (pos p0 p5 n) (pos p1 p5 n) (pos p2 p5 n) (pos p3 p5 n) (pos p4 p5 n) (pos p5 p5 n)
  )
  (:goal
    (and (pos p5 p2 x))
  )
)