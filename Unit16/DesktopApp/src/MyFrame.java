import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;

public class MyFrame extends JFrame {
    private FullForm fullForm = new FullForm();
    private SimpleForm simpleForm = new SimpleForm();

    {
        setContentPane(fullForm);
        setMinimumSize(new Dimension(350, 250));
        setTitle("Форма ввода данных");
        fullForm.addApplyListener(e -> switchFullPanel());
        simpleForm.addApplyListener(e -> switchSimplePanel());

        getRootPane().getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW)
                .put(KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, InputEvent.CTRL_DOWN_MASK, false),
                        "switchForms");

        getRootPane().getActionMap()
                .put("switchForms", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                switchForm();
            }
        });

    }

    protected void switchForm() {
        if (getContentPane() == simpleForm) {
            switchSimplePanel();
        } else if (getContentPane() == fullForm) {
            switchFullPanel();
        } else throw new IllegalStateException();
    }

    protected void switchFullPanel() {
        Person person = fullForm.getPerson();
        if (canSwitch(checkPerson(person))) {
            simpleForm.setPerson(person);
            setContentPanel(simpleForm);
        }
    }

    protected void switchSimplePanel() {
        Person person = simpleForm.getPerson();
        if (canSwitch(checkPerson(person))) {
            fullForm.setPerson(person);
            setContentPanel(fullForm);
        }
    }

    protected void setContentPanel(Container container) {
        setContentPane(container);
        getContentPane().revalidate();
        getContentPane().repaint();
    }

    public static final int
            PERSON_OK = 0,
            SURNAME_OR_NAME_MISSING = 1,
            PATRONYMIC_MISSING = 2,
            INCORRECT_PERSON = 3;

    public static boolean isEmpty(String value) {
        return value == null || value.trim().isEmpty();
    }

    public static int checkPerson(Person person) {
        if (person == null) return INCORRECT_PERSON;
        else if (isEmpty(person.getSurname()) || isEmpty(person.getSurname().trim()))
            return SURNAME_OR_NAME_MISSING;
        else if (isEmpty(person.getPatronymic()))
            return PATRONYMIC_MISSING;
        else return PERSON_OK;
    }

    protected boolean canSwitch(int personStatus) {
        if (personStatus == PERSON_OK) {
            return true;

        } else if (INCORRECT_PERSON == personStatus) {
            JOptionPane.showOptionDialog(this,
                    "Необходимо заполнить/проверить данные", "Некорректный ввод",
                    JOptionPane.DEFAULT_OPTION, JOptionPane.ERROR_MESSAGE, null, null, null);
            return false;
        } else if (SURNAME_OR_NAME_MISSING == personStatus) {
            JOptionPane.showOptionDialog(this,
                    "Необходимо заполнить имя или фамилию", "Введены не все данные",
                    JOptionPane.DEFAULT_OPTION, JOptionPane.WARNING_MESSAGE, null, null, null);
            return false;
        } else if (personStatus == PATRONYMIC_MISSING) {
            return (JOptionPane.YES_OPTION == JOptionPane.showOptionDialog(this,
                    "Не заполнено отчество, продолжить без него?", "Введены не все данные",
                    JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, null, null));
        } else throw new IllegalArgumentException();
    }
}
