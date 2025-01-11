// Implementieren Sie jeweils eine search Methode für Ihre Listen und den Vektor.
package uebung;

import java.util.HashSet;
import java.util.Set;

class U09_AFG02_DoubleList<T> {
    class ListElem {
        public ListElem(T obj, ListElem next, ListElem prev) {
            m_Next = next;
            m_Prev = prev;
            m_Elem = obj;
            if (m_Next != null)
                m_Next.setPrev(this);
            if (m_Prev != null)
                m_Prev.setNext(this);
        }

        private ListElem m_Next;
        private ListElem m_Prev;
        private T m_Elem;

        public T getElement() {
            return m_Elem;
        }

        public ListElem getNext() {
            return m_Next;
        }

        public ListElem getPrev() {
            return m_Prev;
        }

        public void setNext(ListElem next) {
            m_Next = next;
        }

        public void setPrev(ListElem prev) {
            m_Prev = prev;
        }
    }

    private ListElem m_Head;

    public void push_front(T obj) {
        ListElem newElem = new ListElem(obj, m_Head, null);
        if (m_Head != null)
            m_Head.setPrev(newElem);
        m_Head = newElem;
    }

    public void push_back(T obj) {
        if (isEmpty()) {
            ListElem newElem = new ListElem(obj, null, null);
        } else {
            ListElem elem = m_Head;
            while (elem.getNext() != null) {
                elem.getNext();
            }
            ListElem newElem = new ListElem(obj, null, elem);
            elem.setNext(newElem);
        }
    }

    public T pop_front() {
        if (isEmpty()) {
            return null;
        } else {
            T res = m_Head.getElement();
            m_Head = m_Head.getNext();
            if (m_Head != null)
                m_Head.setPrev(null);
            return res;
        }
    }

    public T pop_back() {
        if (isEmpty()) {
            return null;
        } else if (m_Head.getNext() == null) {
            T res = m_Head.getElement();
            m_Head = null;
            return res;
        } else {
            ListElem elem = m_Head;
            while (elem.getNext().getNext() != null) {
                elem = elem.getNext();
            }
            T res = elem.getNext().getElement();
            elem.setNext(null);
            return res;
        }
    }

    public void delete(ListElem elem2Delete) {
        if (elem2Delete != null) {
            if (m_Head == elem2Delete)
                m_Head = m_Head.getNext();
            if (elem2Delete.getPrev() != null)
                elem2Delete.getPrev().setNext(elem2Delete.getNext());
            if (elem2Delete.getNext() != null)
                elem2Delete.getNext().setNext(elem2Delete.getPrev());
        }
    }

    public T seqSearch(T key) {
        ListElem elem = m_Head;
        T res = null;
        while (elem != null) {
            if (elem.getElement() == key) {
                res = elem.getElement();
                break;
            }
            elem = elem.getNext();
        }
        return res;
    }

    @Override
    public String toString() {
        System.out.println("toString() wurde aufgerufen");
        StringBuilder result = new StringBuilder();
        ListElem elem = m_Head;
        Set<ListElem> seen = new HashSet<>();

        while (elem.getNext() != null) {
            if (seen.contains(elem)) {
                result.append(" <-> [Zyklus erkannt]");
                break;
            }
            seen.add(elem);
            result.append(elem.getElement());
            if (elem.getNext() != null) {
                result.append(" <-> ");
            }
            elem = elem.getNext();
        }

        if (result.length() == 0) {
            result.append("Liste ist leer");
        } else if (elem == null) {
            result.append(" <-> null");
        }
        return result.toString();
    }

    public boolean isEmpty() {
        return m_Head == null;
    }
}

class U09_AFG02b_SingleList<T> {
    class ListElem {
        public ListElem(T obj, ListElem next) {
            m_Next = next;
            m_Elem = obj;
        }

        public T getElement() {
            return m_Elem;
        }

        public ListElem getNext() {
            return m_Next;
        }

        public void setNext(ListElem next) {
            m_Next = next;
        }

        private ListElem m_Next;
        private T m_Elem;
    }

    private ListElem m_Head;

    public U09_AFG02b_SingleList() {
        m_Head = null;
    }

    public void print() {
        for (ListElem elem = m_Head; elem != null; elem.getNext())
            System.out.println(elem.getElement() + "\t");
    }

    public void push_front(T obj) {
        m_Head = new ListElem(obj, m_Head);
    }

    public void push_back(T obj) {
        if (isEmpty()) {
            m_Head = new ListElem(obj, null);
        } else {
            ListElem elem = m_Head;
            while (elem.getNext() != null)
                elem = elem.getNext();
            elem.setNext(new ListElem(obj, null));
        }
    }

    public T pop_front() {
        if (isEmpty()) {
            return null;
        } else {
            T res = m_Head.getElement();
            m_Head = m_Head.getNext();
            return res;
        }
    }

    public T pop_back() {
        if (isEmpty()) {
            return null;
        } else if (m_Head.getNext() == null) {
            T res = m_Head.getElement();
            m_Head = null;
            return res;
        } else {
            ListElem elem = m_Head;
            while (elem.getNext().getNext() != null)
                elem = elem.getNext();
            T res = elem.getNext().getElement();
            elem.setNext(null);
            return res;
        }
    }

    public boolean isEmpty() {
        return m_Head == null;
    }

    public void delete(ListElem pElem2Delete) {
        if (pElem2Delete != null) {
            if (m_Head == pElem2Delete) {
                m_Head = pElem2Delete.getNext();
            } else {
                for (ListElem tmp = m_Head; tmp != null; tmp = tmp.getNext()) {
                    if (tmp.getNext() == pElem2Delete) {
                        tmp.setNext(pElem2Delete.getNext());
                        return;
                    }
                }
            }
        }
    }

    public T seqSearch(T key) {
        T res = null;
        for (ListElem elem = m_Head; elem != null; elem.getNext())
            if (elem.getElement() == key) {
                res = elem.getElement();
                break;
            }
        return res;
    }

    @Override
    public String toString() {
        StringBuilder result = new StringBuilder();
        ListElem elem = m_Head;
        while (elem != null) {
            result.append(elem.getElement());
            if (elem.getNext() != null)
                result.append(" -> ");
            elem = elem.getNext();
        }
        result.append(" -> null");
        return result.toString();
    }
}

public class U09_AFG02b_List {
    public static void main(String[] args) {
        System.out.println("------------------------------------- Single List");
        U09_AFG02b_SingleList<Integer> sl = new U09_AFG02b_SingleList<>();
        sl.push_front(10);
        sl.push_back(20);
        sl.push_front(5);

        System.out.println(sl);

        sl.pop_front();
        System.out.println(sl);

        sl.push_front(5);
        sl.pop_back();
        System.out.println(sl);

        System.out.println(sl.isEmpty());

        System.out.println("------------------------------------- Double List");
        U09_AFG02_DoubleList<Integer> dl = new U09_AFG02_DoubleList<>();
        dl.push_front(30);
        dl.push_front(40);
        dl.push_front(50);

        System.out.println(dl);

    }
}
