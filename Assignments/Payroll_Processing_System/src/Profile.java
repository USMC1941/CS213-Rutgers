public class Profile {
    /**
     * Employee’s name in the form “lastname,firstname”
     */
    private String name;

    /**
     * Department code: CS, ECE, IT
     */
    private String department;

    private Date dateHired;

    @Override
    public String toString() {}

    /**
     * Compare name, department and dateHired
     */
    @Override
    public boolean equals(Object obj) {}
}
