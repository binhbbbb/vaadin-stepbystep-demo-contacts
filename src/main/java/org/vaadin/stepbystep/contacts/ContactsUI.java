package org.vaadin.stepbystep.contacts;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

import org.vaadin.stepbystep.person.backend.Person;
import org.vaadin.stepbystep.person.backend.PersonService;

import com.vaadin.annotations.Theme;
import com.vaadin.cdi.CDIUI;
import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.server.VaadinRequest;
import com.vaadin.ui.Grid;
import com.vaadin.ui.HorizontalSplitPanel;
import com.vaadin.ui.UI;

/**
 * This UI is the application entry point. A UI may either represent a browser window 
 * (or tab) or some part of a html page where a Vaadin application is embedded.
 * <p>
 * The UI is initialized using {@link #init(VaadinRequest)}. This method is intended to be 
 * overridden to add component to the user interface and initialize non-component functionality.
 */
@Theme("mytheme")
@CDIUI("")
public class ContactsUI extends UI {

	HorizontalSplitPanel splitter = new HorizontalSplitPanel();
	Grid grid = new Grid();
	PersonView editor = new PersonView(item -> savePerson(item));

    @Inject
    PersonService service;

    @PostConstruct
    void load() {
        service.loadData();

        grid.addSelectionListener(evt -> {
        		Person selectedItem = (Person) grid.getSelectedRow();
        		if(selectedItem == null) {
        			selectDefault();
        		}else {
        			editor.setPerson(selectedItem);
        		}
        });

        BeanItemContainer<Person> container = new BeanItemContainer<>(Person.class, service.getEntries());
        grid.setContainerDataSource(container);

        selectDefault();
    }

	public void selectDefault() {
		grid.select(grid.getContainerDataSource().getIdByIndex(0));
	}
	
    private void savePerson(Person item) {
    		service.save(item);

        grid.refreshRows(item);
    }
    
    @Override
    protected void init(VaadinRequest vaadinRequest) {

    		splitter.setSizeFull();
    		grid.setSizeFull();
    		editor.setSizeFull();
    		
    		splitter.setFirstComponent(grid);
    		splitter.setSecondComponent(editor);
        
        setContent(splitter);
    }
}
