package seedu.address.ui;

import java.util.logging.Logger;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.Region;
import seedu.address.commons.core.LogsCenter;
import seedu.address.model.module.member.Member;

/**
 * Panel containing the list of members.
 */
public class MemberListPanel extends UiPart<Region> {
    private static final String FXML = "MemberListPanel.fxml";
    private final Logger logger = LogsCenter.getLogger(MemberListPanel.class);

    @FXML
    private Label memberListTitle;
    @FXML
    private ListView<Member> memberListView;

    /**
     * Creates a {@code MemberListPanel} with the given {@code ObservableList} and {@code Event}.
     */
    public MemberListPanel(ObservableList<Member> memberList) {
        super(FXML);
        memberListTitle.setText("Member List");
        memberListView.setItems(memberList);
        memberListView.setCellFactory(listView -> new MemberListViewCell());
    }

    /**
     * Custom {@code ListCell} that displays the graphics of a {@code Member} using a {@code MemberCard}.
     */
    class MemberListViewCell extends ListCell<Member> {
        @Override
        protected void updateItem(Member member, boolean empty) {
            super.updateItem(member, empty);

            if (empty || member == null) {
                setGraphic(null);
                setText(null);
            } else {
                setGraphic(new MemberCard(member, getIndex() + 1).getRoot());
            }
        }
    }
}
