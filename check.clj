(ns spekl-package-manager.check
 (:require [clojure.java.io :as io]
           [clojure.tools.logging :as log]
           [spekl-package-manager.util :as util]

           ))

(def report-file "findbugs-report.html")
(def report-file-xml "findbugs-report.xml")

(defn get-open-command [file]
  (let [platform (util/get-my-platform)]
    (case platform
      "windows" (list "cmd" "/c" "start" file) 
      "linux"   (list "xdg-open" file)
      "osx"     (list "open" file)
      nil
      )))

(defn open-report [file]
  (log/info "Opening report...")
  (let [open-command (get-open-command file)]
    (if (= nil open-command)
      (do (log/info "Your report is available in the file" report-file))
      (apply run open-command )

      )))

(defcheck default
  (log/info  "Running findbugs... report will open after running...")  
  (let [result  (run-no-output "java" "-jar" "${findbugs-3.0.1/lib/findbugs.jar}" "-textui" "-html" *project-files*)]
    (if (= 1 (result :exit))
      (println (result :out))
      (do
        (spit report-file (result :out))
        (open-report report-file)))))


(defcheck html
  (log/info  "Running findbugs... report will open after running...")  
  (let [result  (run-no-output "java" "-jar" "${findbugs-3.0.1/lib/findbugs.jar}" "-textui" "-html" *project-files*)]
    (if (= 1 (result :exit))
      (println (result :out))
      (spit report-file (result :out)))))



(defcheck xml
  (log/info  "Running findbugs [XML]... report will be available at" report-file-xml "after running")  
  (let [result  (run-no-output "java" "-jar" "${findbugs-3.0.1/lib/findbugs.jar}" "-textui" "-xml" *project-files*)]
    (if (= 1 (result :exit))
      (println (result :out))
      (spit report-file-xml (result :out)))))

