(ns zrch-logic.core
  (:refer-clojure :exclude [==])
  (:use [clojure.core.logic])
  (:require [clojure.core.logic.fd :as fd]))

;; =============================================================================
;; Basics

(comment
  (run* [q]
    (== q true))

  (run* [q]
    (== q true)
    (== q false))

  (run* [q]
    (== false q)
    (== true q))

  (run* [x y]
    (conde
      [(== x 'tea)]
      [(== x 'coffee)])
    (== y 'biscuits))

  (run* [q]
    (conso 'cat '(dog bird) q))

  (run* [q]a
    (conso q '(dog bird) '(cat dog bird)))

  (run* [q]
    (fresh [x]
      (conso x q '(cat dog bird))))

  (run* [q]
    (fresh [head tail]
      (conso head tail q)))

  (run* [q]
    (membero q '(cat dog bird)))

  (run 5 [q]
    (membero 'cat q))
  )

;; =============================================================================
;; Finite Domains

(comment
  (run* [q]
    (fd/in q (fd/interval 1 10)))

  (run* [x y z :as q]
    (fd/in x y z (fd/interval 5))
    (fd/+ x y z))

  (run* [x y z :as q]
    (fd/in x y z (fd/interval 5))
    (fd/distinct q)
    (fd/+ x y z))

  (run* [x y z :as q]
    (fd/in x y z (fd/interval 5))
    (fd/distinct q)
    (fd/< x y) (fd/< y z)
    (fd/+ x y z))

  (run* [x y]
    (fd/in x y (fd/interval 0 9))
    (fd/eq
      (= (+ x y) 9)
      (= (+ (* x 2) (* y 4)) 24)))
  )

;; =============================================================================
;; Features

(comment
  (run* [q]
    (fresh [x y]
      (== x {:foo 1})
      (featurec x {:foo y})))

  (run* [q]
    (fresh [x y]
      (== x {:foo 1})
      (featurec x {:bar y})))
  
  (run* [q]
    (fresh [x y]
      (fd/in y (fd/interval 1 10))
      (== x {:foo {:bar y}})
      (featurec x {:foo {:bar q}})
      (fd/in q (fd/interval 1 3))))
  )
