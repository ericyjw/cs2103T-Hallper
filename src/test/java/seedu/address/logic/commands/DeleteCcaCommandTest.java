package seedu.address.logic.commands;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.CommandTestUtil.showPersonAtIndex;
import static seedu.address.testutil.TypicalCcas.getTypicalBudgetBook;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_PERSON;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import org.junit.Test;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.CommandHistory;
import seedu.address.model.AddressBook;
import seedu.address.model.BudgetBook;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.cca.Budget;
import seedu.address.model.cca.Cca;
import seedu.address.model.cca.CcaName;
import seedu.address.model.person.Person;

//@@author ericyjw

/**
 * Contains integration tests (interaction with the Model, UndoCommand and RedoCommand) and unit tests for
 * {@code DeleteCcaCommand}.
 */
public class DeleteCcaCommandTest {

    private Model model = new ModelManager(getTypicalBudgetBook(), new UserPrefs());
    private CommandHistory commandHistory = new CommandHistory();

    @Test
    public void execute_validDeletingOfCca_success() {
        Cca ccaToDelete = model.getFilteredCcaList().get(0);
        CcaName ccaName = ccaToDelete.getName();
        DeleteCcaCommand deleteCcaCommand = new DeleteCcaCommand(ccaName);

        String expectedMessage = String.format(DeleteCcaCommand.MESSAGE_DELETE_CCA_SUCCESS, ccaToDelete);

        ModelManager expectedModel = new ModelManager(new AddressBook(), model.getBudgetBook(), new UserPrefs(),
            model.getExistingEmails());
        expectedModel.deleteCca(ccaToDelete);
        expectedModel.commitBudgetBook();

        assertCommandSuccess(deleteCcaCommand, model, commandHistory, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidDeletingOfCca_throwsCommandException() {
        CcaName ccaTobeDeleted = new CcaName("Soccer");
        DeleteCcaCommand deleteCcaCommand = new DeleteCcaCommand(ccaTobeDeleted);

        assertCommandFailure(deleteCcaCommand, model, commandHistory, Messages.MESSAGE_INVALID_CCA);
    }

//    @Test
//    public void execute_validIndexFilteredList_success() {
//        showPersonAtIndex(model, INDEX_FIRST_PERSON);
//
//        Person personToDelete = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
//        DeleteCommand deleteCommand = new DeleteCommand(INDEX_FIRST_PERSON);
//
//        String expectedMessage = String.format(DeleteCommand.MESSAGE_DELETE_PERSON_SUCCESS, personToDelete);
//
//        Model expectedModel = new ModelManager(model.getAddressBook(), new BudgetBook(), new UserPrefs(),
//            model.getExistingEmails());
//        expectedModel.deletePerson(personToDelete);
//        expectedModel.commitAddressBook();
//        showNoPerson(expectedModel);
//
//        assertCommandSuccess(deleteCommand, model, commandHistory, expectedMessage, expectedModel);
//    }
//
//    @Test
//    public void execute_invalidIndexFilteredList_throwsCommandException() {
//        showPersonAtIndex(model, INDEX_FIRST_PERSON);
//
//        Index outOfBoundIndex = INDEX_SECOND_PERSON;
//        // ensures that outOfBoundIndex is still in bounds of address book list
//        assertTrue(outOfBoundIndex.getZeroBased() < model.getAddressBook().getPersonList().size());
//
//        DeleteCommand deleteCommand = new DeleteCommand(outOfBoundIndex);
//
//        assertCommandFailure(deleteCommand, model, commandHistory, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
//    }

//    @Test
//    public void executeUndoRedo_validIndexUnfilteredList_success() throws Exception {
//        Person personToDelete = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
//        DeleteCommand deleteCommand = new DeleteCommand(INDEX_FIRST_PERSON);
//        Model expectedModel = new ModelManager(model.getAddressBook(), new BudgetBook(), new UserPrefs(),
//            model.getExistingEmails());
//        expectedModel.deletePerson(personToDelete);
//        expectedModel.commitAddressBook();
//
//        // delete -> first person deleted
//        deleteCommand.execute(model, commandHistory);
//
//        // undo -> reverts addressbook back to previous state and filtered person list to show all persons
//        expectedModel.undoAddressBook();
//        assertCommandSuccess(new UndoCommand(), model, commandHistory, UndoCommand.MESSAGE_SUCCESS, expectedModel);
//
//        // redo -> same first person deleted again
//        expectedModel.redoAddressBook();
//        assertCommandSuccess(new RedoCommand(), model, commandHistory, RedoCommand.MESSAGE_SUCCESS, expectedModel);
//    }
//
//    @Test
//    public void executeUndoRedo_invalidIndexUnfilteredList_failure() {
//        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredPersonList().size() + 1);
//        DeleteCommand deleteCommand = new DeleteCommand(outOfBoundIndex);
//
//        // execution failed -> address book state not added into model
//        assertCommandFailure(deleteCommand, model, commandHistory, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
//
//        // single address book state in model -> undoCommand and redoCommand fail
//        assertCommandFailure(new UndoCommand(), model, commandHistory, UndoCommand.MESSAGE_FAILURE);
//        assertCommandFailure(new RedoCommand(), model, commandHistory, RedoCommand.MESSAGE_FAILURE);
//    }

//    /**
//     * 1. Deletes a {@code Person} from a filtered list.
//     * 2. Undo the deletion.
//     * 3. The unfiltered list should be shown now. Verify that the index of the previously deleted person in the
//     * unfiltered list is different from the index at the filtered list.
//     * 4. Redo the deletion. This ensures {@code RedoCommand} deletes the person object regardless of indexing.
//     */
//    @Test
//    public void executeUndoRedo_validIndexFilteredList_samePersonDeleted() throws Exception {
//        DeleteCommand deleteCommand = new DeleteCommand(INDEX_FIRST_PERSON);
//        Model expectedModel = new ModelManager(model.getAddressBook(), new BudgetBook(), new UserPrefs(),
//            model.getExistingEmails());
//
//        showPersonAtIndex(model, INDEX_SECOND_PERSON);
//        Person personToDelete = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
//        expectedModel.deletePerson(personToDelete);
//        expectedModel.commitAddressBook();
//
//        // delete -> deletes second person in unfiltered person list / first person in filtered person list
//        deleteCommand.execute(model, commandHistory);
//
//        // undo -> reverts addressbook back to previous state and filtered person list to show all persons
//        expectedModel.undoAddressBook();
//        assertCommandSuccess(new UndoCommand(), model, commandHistory, UndoCommand.MESSAGE_SUCCESS, expectedModel);
//
//        assertNotEquals(personToDelete, model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased()));
//        // redo -> deletes same second person in unfiltered person list
//        expectedModel.redoAddressBook();
//        assertCommandSuccess(new RedoCommand(), model, commandHistory, RedoCommand.MESSAGE_SUCCESS, expectedModel);
//    }

    @Test
    public void equals() {
        DeleteCcaCommand deleteFirstCommand = new DeleteCcaCommand(new CcaName("Soccer"));
        DeleteCcaCommand deleteSecondCommand = new DeleteCcaCommand(new CcaName("Hockey"));

        // same object -> returns true
        assertTrue(deleteFirstCommand.equals(deleteFirstCommand));

        // same values -> returns true
        DeleteCcaCommand deleteFirstCommandCopy = new DeleteCcaCommand(new CcaName("Soccer"));
        assertTrue(deleteFirstCommand.equals(deleteFirstCommandCopy));

        // different types -> returns false
        assertFalse(deleteFirstCommand.equals(1));

        // null -> returns false
        assertFalse(deleteFirstCommand.equals(null));

        // different person -> returns false
        assertFalse(deleteFirstCommand.equals(deleteSecondCommand));
    }

    /**
     * Updates {@code model}'s filtered list to show no cca.
     */
    private void showNoPerson(Model model) {
        model.updateFilteredCcaList(p -> false);

        assertTrue(model.getFilteredCcaList().isEmpty());
    }
}