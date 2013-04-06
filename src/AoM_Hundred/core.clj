(ns AoM-Hundred.core
    (:require [net.cgrand.enlive-html :as html]
     [clojure.java.io :as io]))

(def base-urls
     ["http://www.artofmanliness.com/2008/05/14/100-must-read-books-the-essential-mans-library/"
      "http://www.artofmanliness.com/2008/05/14/100-must-read-books-the-essential-mans-library/2/"
      "http://www.artofmanliness.com/2008/05/14/100-must-read-books-the-essential-mans-library/3/"
      "http://www.artofmanliness.com/2008/05/14/100-must-read-books-the-essential-mans-library/4/"])

(defn fetch-url [url]
     (html/html-resource (java.net.URL. url)))

(defn page-books [n]
  (map html/text
       (html/select (fetch-url (nth base-urls n))
                    #{[:p :strong]})))


(defn list-page [n]
  (filter (fn [x] (not= x "Pages:")) (map #(str %1) (page-books n))))


(defn list-books []
  (reduce into (map (fn [x] (list-page x)) (range 4))))

(defn print-list [books]
  (with-open [w (io/writer "books.txt")]
             (doseq [line (map (fn [x] (str x)) books)]
                    (.write w (str line "\n")))))

(defn -main
  "Print out a list of the 100 must-read books."
  [& args]
  (print-list (list-books)))
