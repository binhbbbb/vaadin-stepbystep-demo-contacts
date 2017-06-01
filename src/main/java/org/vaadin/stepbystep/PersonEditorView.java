package org.vaadin.stepbystep;

import com.vaadin.data.Binder;
import org.vaadin.stepbystep.person.backend.Person;

import java.util.function.Consumer;

/**
 * Created by amahdy on 6/1/17.
 */
public class PersonEditorView extends PersonEditorDesign {

    Binder<Person> binder = new Binder<>(Person.class);

    public PersonEditorView(Consumer<Person> consumer) {
        binder.bindInstanceFields(this);

        save.addClickListener(evt -> consumer.accept(binder.getBean()));
    }

    public void setPerson(Person value) {
        binder.setBean(value);
    }
}
