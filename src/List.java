 

/**
 * <p>Materialien zu den zentralen
 * Abiturpruefungen im Fach Informatik ab 2012 in 
 * Nordrhein-Westfalen.</p>
 * <p>Klasse List</p>
 * <p>Objekte der Klasse List verwalten beliebig viele, 
 * linear angeordnete Objekte. Auf hoechstens ein Listenobjekt, 
 * aktuelles Objekt genannt, kann jeweils zugegriffen werden. 
 * Wenn eine Liste leer ist, vollstaendig durchlaufen wurde oder 
 * das aktuelle Objekt am Ende der Liste geloescht wurde, gibt es 
 * kein aktuelles Objekt. Das erste oder das letzte Objekt einer 
 * Liste koennen durch einen Auftrag zum aktuellen Objekt gemacht werden. 
 * Außerdem kann das dem aktuellen Objekt folgende Listenobjekt 
 * zum neuen aktuellen Objekt werden. Das aktuelle Objekt kann gelesen, 
 * veraendert oder geloescht werden. Ausserdem kann vor dem aktuellen 
 * Objekt ein Listenobjekt eingefügt werden.</p>
 * 
 * <p>NW-Arbeitsgruppe: Materialentwicklung zum Zentralabitur 
 * im Fach Informatik</p>
 * 
 * @version 2011-03-31
 */
public class List

{ private Node first, tail, current;

    // Node
  private class Node { 
        private Object contentObj;
        private Node nextNode;

        public Node(Object pContent) { 
            contentObj = pContent;
            nextNode = null;
        }

        public void setContent(Object pContent) { 
            contentObj = pContent; 
        }

        public Object content() { 
            return contentObj; 
        }

        public void setNext(Node pNext) { 
            nextNode = pNext; 
        }

        public Node getNext() { 
            return nextNode; 
        }

    } // Ende der Klasse Node
 
    /**
     * Eine leere Liste wird erzeugt.
     */
    public List() {
        tail = new Node(null); // Dummy
        first = tail;
        tail.setNext(tail);
        /* Der next-Zeiger des hinteren Dummy-Elementes
         * zeigt auf das vorangehende Element.
         */
        current=first;
    }
  
    /**
     * Die Anfrage liefert den Wert true, wenn die Liste 
     * keine Objekte enthaelt, sonst liefert sie den Wert false.
     * @return true, wenn die Liste leer ist, sonst false
     */
    public boolean isEmpty() { 
        return first == tail; 
    }
 
    /**
     * Die Anfrage liefert den Wert true, wenn es ein
     * aktuelles Objekt gibt, sonst 
     * liefert sie den Wert false.
     * @return true, falls Zugriff moeglich, sonst false
     */
    public boolean hasAccess() { 
        return (!this.isEmpty()) && (current != tail);
    }
  
    /**
     * Falls die Liste nicht leer ist, es ein aktuelles 
     * Objekt gibt und dieses nicht das letzte Objekt der 
     * Liste ist, wird das dem aktuellen Objekt in der Liste 
     * folgende Objekt zum aktuellen Objekt, andernfalls gibt 
     * es nach Ausführung des Auftrags kein aktuelles Objekt, 
     * d.h. hasAccess() liefert den Wert false.
     */
    public void next() { 
        if (this.hasAccess())
            current = current.getNext();
    }
 
    /**
     * Falls die Liste nicht leer ist, wird das erste 
     * Objekt der Liste aktuelles Objekt. 
     * Ist die Liste leer, geschieht nichts.
     */
    public void toFirst() {
        if (!this.isEmpty())  
            current = first; 
    }
 
    /**
     * Falls die Liste nicht leer ist, wird das 
     * letzte Objekt der Liste aktuelles Objekt. 
     * Ist die Liste leer, geschieht nichts.
     */
    public void toLast() {
        if (!this.isEmpty())  
            current = tail.getNext(); 
    }

    /**
     * Falls es ein aktuelles Objekt gibt 
     * (hasAccess() == true), wird das aktuelle Objekt 
     * zurueckgegeben, andernfalls (hasAccess()== false) 
     * gibt die Anfrage den Wert null zurueck.
     * @return Inhaltsobjekt
     */
    public Object getObject() {   
        if (this.hasAccess())
            return current.content();
        else 
            return null;
    }
 
    /**
     * Falls es ein aktuelles Objekt gibt (hasAccess() == true)
     * und pObject ungleich null ist, wird das aktuelle Objekt 
     * durch pObject ersetzt. Sonst bleibt die Liste unveraendert.
     * @param pObject Inhaltsobjekt
     */
    public void setObject(Object pObject) { 
        if (pObject != null && this.hasAccess() )
            current.setContent(pObject); 
    }

    /**
     * Ein neues Objekt pObject wird am Ende der Liste eingefuegt. 
     * Das aktuelle Objekt bleibt unveraendert. Wenn die Liste 
     * leer ist, wird das Objekt pObject in die Liste eingefuegt 
     * und es gibt weiterhin kein aktuelles Objekt 
     * (hasAccess() == false). Falls pObject gleich null ist, 
     * bleibt die Liste unveraendert. 
     *@param pObject Inhaltsobject
     */    
    public void append(Object pObject) { 
        if (pObject != null) {  
            Node lNewNode,lPos0;
            lPos0 = current;
            lNewNode = new Node(pObject);
            lNewNode.setNext(tail);
            if (this.isEmpty())
                first = lNewNode;
            else {  
                Node lPrevious = tail.getNext();
                lPrevious.setNext(lNewNode);
            }  
            tail.setNext(lNewNode);
            current = lPos0;
        }
    }  

    /**
     *Falls es ein aktuelles Objekt gibt (hasAccess() == true),
     *wird ein neues Objekt vor dem aktuellen Objekt in die 
     *Liste eingefuegt. Das aktuelle Objekt bleibt unveraendert. 
     *Wenn die Liste leer ist, wird pObject in die Liste eingefuegt
     *und es gibt weiterhin kein aktuelles Objekt 
     *(hasAccess() == false). Falls es kein aktuelles Objekt gibt 
     *(hasAccess() == false) und die Liste nicht leer ist oder 
     *pObject gleich null ist, bleibt die Liste unveraendert.
     *@param pObject Inhaltsobjekt
     */
    public void insert(Object pObject) {
        if (pObject != null) {
          Node lNewNode,lFront,lPos;
          if (this.isEmpty())
            this.append(pObject);
          else 
          if (this.hasAccess() ) {
            lPos = current;
            lNewNode = new Node(pObject);
            lNewNode.setNext(current);
            if (lPos == first )
              first = lNewNode;
            else {
              this.toFirst();
              lFront = current;
              while (this.hasAccess() & !(current == lPos)) {
                 lFront = current;
                 this.next();
              }
              lFront.setNext(lNewNode);  
            }          
            current=lPos;
          }
        }
    }

    /**
     * Die Liste pList wird an die Liste angehaengt. Anschliessend 
     * wird pList eine leere Liste. Das aktuelle Objekt bleibt unveraendert. 
     * Falls pList null oder eine leere Liste ist, bleibt die Liste 
     * unveraendert.
     * @param pList Liste
     */
    public void concat(List pList) {
      Node lCurrent1, lCurrent2, lPos0;
      if (pList != null && !pList.isEmpty() ) {
        if (this.isEmpty() ) {
          first = pList.first;
          tail = pList.tail;
          current = tail;
        }
        else {
          lPos0 = current;
          current = tail.getNext();
          lCurrent1 = current;
          pList.toFirst();
          current = pList.current;
          lCurrent2 = pList.current;
          lCurrent1.setNext(lCurrent2);
          if (lPos0 != tail)
            current = lPos0;
          else
            current = pList.tail;
          tail = pList.tail;
        }
        // pList wird zur leeren Liste
        pList.tail = new Node(null); // Dummy
        pList.first = pList.tail;
        pList.tail.setNext(tail);
        pList.current = pList.tail;
      }
    }

    /**
     * Falls es ein aktuelles Objekt gibt (hasAccess() == true),
     * wird das aktuelle Objekt geloescht und das Objekt hinter 
     * dem gelaeschten Objekt wird zum aktuellen Objekt. Wird das 
     * Objekt, das am Ende der Liste steht, geloescht, gibt es kein 
     * aktuelles Objekt mehr (hasAccess() == false). Wenn die Liste 
     * leer ist oder es kein aktuelles Objekt gibt (hasAccess() == false),
     * bleibt die Liste unveraendert.
     */
    public void remove() { 
        Node lPos, lFront;
        if (this.hasAccess() ) {
            if (current == first ) {
                first = current.getNext();
                if (current.getNext() == tail)
                    tail.setNext(first);
                current = first;  
            }
            else {
                lPos = current;
                this.toFirst();
                lFront = current;
                while (this.hasAccess() && !(current == lPos)) {
                    lFront = current;
                    this.next();
                }
                lFront.setNext(lPos.getNext());
                current = lFront.getNext();
                if (current == tail)
                    tail.setNext(lFront);
            }  
        }
    }  

}
