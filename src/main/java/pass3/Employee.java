package pass3;

public class Employee {

    private final String m_id;
    private final String m_lastName;
    private final String m_firstName;

    public Employee(final String id, final String firstName, final String lastName) {
        m_id = id;
        m_lastName = lastName;
        m_firstName = firstName;
    }

    public String getId() {
        return m_id;
    }

    public String getLastName() {
        return m_lastName;
    }

    public String getFirstName() {
        return m_firstName;
    }
}
