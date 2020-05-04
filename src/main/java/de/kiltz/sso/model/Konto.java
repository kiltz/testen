package de.kiltz.sso.model;

import java.util.StringJoiner;

/**
 * @author tz
 */
public class Konto {

    private Long id;
    private String vorname;
    private String nachname;
    private String email;
    private String passwort;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getVorname() {
        return vorname;
    }

    public void setVorname(String vorname) {
        this.vorname = vorname;
    }

    public String getNachname() {
        return nachname;
    }

    public void setNachname(String nachname) {
        this.nachname = nachname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPasswort() {
        return passwort;
    }

    public void setPasswort(String passwort) {
        this.passwort = passwort;
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", Konto.class.getSimpleName() + "[", "]")
                .add("id=" + id)
                .add("vorname='" + vorname + "'")
                .add("nachname='" + nachname + "'")
                .add("email='" + email + "'")
                .add("passwort='" + passwort + "'")
                .toString();
    }
}
