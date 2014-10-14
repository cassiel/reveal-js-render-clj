;; `render` is built-in. It expects `:theme` (a keyword naming one of reveal.js's built-in themes),
;; `title` and `author` (as meta-information - you'll still need to add a title slide), and `:slides`
;; containing a sequence of `:section` bodies.

(render :theme :moon
        :title "Example Slides"
        :author "Nick Rothwell"

        :slides [[:section
                  [:h1 "Example Slides"]
                  [:h2 "Nick Rothwell"]]

                 [:section
                  [:p "This is a paragraph."]
                  [:p "This is " [:i "also"] " a paragraph."]]

                 [:section
                  [:p "Here is some code, as separate lines."]

                  (code "Line 1"
                        "Line 2")]

                 [:section
                  [:p "Code can also be entered in a single string."]
                  [:p "(Leading and trailing newlines are removed.)"]

                  (code "
Line 1
Line 2
")]

                 [:section
                  [:h2 "Including Code"]
                  [:p "Code can also be included verbatim from a separate file (in the " [:code "include"] " subdirectory)"]
                  (include-code "included-html.html")]

                 [:section
                  [:h2 "Three Images from Another Project"]
                  [:p "(Implicit: images come from " [:code "images"] " subdirectory.)"]
                  [:div
                   (image-h 200 "mini-cla-statements.jpg")
                   (image-h 200 "mini-cla-3d.jpg")
                   (image-h 200 "mini-cla-history.jpg")]
                  [:p "Generative editor for statements"]
                  [:p "Visualisation of textual instructions"]
                  [:p "Documentation and change history"]]

                 [:section
                  [:h2 "YouTube Video (link)"]
                  (youtube-link "N3vYiJ1CR1g" "Cassiel / Nina Kov")]

                 [:section
                  [:h2 "YouTube Embed"]
                  (youtube-embed "N3vYiJ1CR1g")
                  [:p "Cassiel / Nina Kov"]]

                 [:section
                  [:h2 "Vimeo Video (link)"]
                  (vimeo-link "85954954" "Chisato Minamimura")]

                 [:section
                  [:h2 "Vimeo Embed"]
                  (vimeo-embed "85954954")
                  [:p "Chisato Minamimura"]]

                 [:section {:data-state "alert"}
                  [:p "An important point!"]]

                 [:section
                  [:h2 "Get the code!"]
                  [:p (github "cassiel/reveal.js"
                              "cassiel's reveal.js")]
                  [:p (github "cassiel/reveal-js-render-clj"
                              "These slides")]]

                 [:section
                  [:h4 "This demo deck by"]
                  [:p "nick rothwell"]
                  [:p "www.cassiel.com"]
                  [:p "@cassieldotcom"]]])
