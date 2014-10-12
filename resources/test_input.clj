(let [IMAGE-ROOT-URL "images/"
      VIDEO-ROOT-URL "movies/"

      image-h (fn [h f]
                [:img {:height h
                       :style "margin:10px; vertical-align:middle"
                       :src (str IMAGE-ROOT-URL f)}])

      image (partial image-h 480)

      video (fn  [f]
              [:video {:height 480
                       :controls 1
                       :data-autoplay 1}
               [:source {:src (str VIDEO-ROOT-URL f)}]])

      github (fn [stem text]
               [:a {:href (str "https://github.com/" stem)} text])]

  (render :theme :beige
          :title "This is the Title"
          :author "Nick"
          :slides [
                   [:section
                    [:h1 "Slides via Clojure and Hiccup"]
                    [:h2 "Nick Rothwell"]
                    [:h3 "www.cassiel.com"]
                    [:h3 "@cassieldotcom"]]

                   [:section
                    [:h2 "Nick Rothwell / Cassiel"]

                    (image-h 400 "IMG_6760-1.jpg")

                    [:aside.notes
                     "Here are some presenter notes." [:br]
                     "Here are some more."]]

                   [:section
                    [:h2 "Three Images from Another Project"]
                    [:div
                     (image-h 200 "mini-cla-statements.jpg")
                     (image-h 200 "mini-cla-3d.jpg")
                     (image-h 200 "mini-cla-history.jpg")]
                    [:p "Generative editor for statements"]
                    [:p "Visualisation of textual instructions"]
                    [:p "Documentation and change history"]]

                   [:section {:data-state "alert"}
                    [:p "An important point!"]]

                   [:section
                    [:h2 "Get the code!"]
                    [:p (github "cassiel/reveal.js"
                                "cassiel's reveal.js")]
                    [:p (github "cassiel/reveal-js-demo-slides"
                                "These slides")]]

                   [:section
                    [:h4 "This demo deck by"]
                    [:p "nick rothwell"]
                    [:p "www.cassiel.com"]
                    [:p "@cassieldotcom"]]]))
