(ns om-navigate.app
  (:require [om.next :as om :refer-macros [defui]]
            [re-natal.support :as sup]
            [om-navigate.elements :as e]
            [om-navigate.state :as state]
            [om-navigate.navigate :as nav]
            [om-navigate.banner :as banner]
            [om-navigate.simple-stack :as sstack]
            [om-navigate.simple-tabs :as stabs]))

(def example-routes
  {:SimpleStack {:name        "Stack Example"
                 :description "A card stack"
                 :screen      sstack/SimpleStack}
   :SimpleTabs {:name        "Tabs Example"
                :description "Tabs following platform conventions"
                :screen      stabs/SimpleTabs}})

(def styles
  {:item 
     {:backgroundColor   "#fff"
      :paddingHorizontal 16
      :paddingVertical   12
      :borderBottomWidth 1
      :borderBottomColor "#ddd"}
   :image 
     {:width        120
      :height       120
      :alignSelf    "center"
      :marginBottom 20
      :resizeMode   "contain"}
   :title 
     {:fontSize   16
      :fontWeight "bold"
      :color      "#444"}
   :description 
     {:fontSize 13
      :color    "#999"}})

(def banner (om/factory banner/Banner))

(defui MainScreen
  Object
  (render [this]
    (e/scroll-view nil
      (banner)
      (map
        (fn [[key {:keys [name description screen]}]]
          (e/touchable-opacity {:key key
                                :onPress #(nav/navigate-to this key)}
            (e/view {:style (:item styles)}
              (e/text {:style (:name styles)} name)
              (e/text {:style (:description styles)} description))))
        example-routes))))

(def AppNavigator (nav/create-stack-navigator 
                    (assoc example-routes :Index {:screen MainScreen})
                    {:initialRouteName "Index"
                     :headerMode "none"
                     :mode "modal"}))
