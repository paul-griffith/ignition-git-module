package com.axone_io.ignition.git.records;

import com.inductiveautomation.ignition.common.user.UserSourceEditCapability;
import com.inductiveautomation.ignition.gateway.localdb.persistence.*;
import com.inductiveautomation.ignition.gateway.model.GatewayContext;
import com.inductiveautomation.ignition.gateway.web.components.RecordEditMode;
import com.inductiveautomation.ignition.gateway.web.components.actions.EditRecordAction;
import com.inductiveautomation.ignition.gateway.web.components.editors.PasswordEditorSource;
import com.inductiveautomation.ignition.gateway.web.components.editors.TextAreaEditorSource;
import com.inductiveautomation.ignition.gateway.web.components.user.UserEditForm;
import com.inductiveautomation.ignition.gateway.web.util.UniqueStringValidator;
import org.apache.wicket.validation.IValidator;
import org.apache.wicket.validation.validator.StringValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import simpleorm.dataset.SFieldFlags;
import simpleorm.dataset.SQuery;

import java.util.List;

public class GitReposUsersRecord extends PersistentRecord {
    private static final Logger logger = LoggerFactory.getLogger(GitReposUsersRecord.class);

    public static final RecordMeta<GitReposUsersRecord> META = new RecordMeta<>(
            GitReposUsersRecord.class, "GitReposUsersRecord");

    @Override
    public RecordMeta<?> getMeta() {
        return META;
    }

    public static final IdentityField Id = new IdentityField(META);
    public static final LongField ProjectId = new LongField(META, "ProjectId");
    public static final ReferenceField<GitProjectsConfigRecord> ProjectName = new ReferenceField<> (META, GitProjectsConfigRecord.META, "ProjectName", ProjectId);
    public static final ReferenceField<GitProjectsConfigRecord> URI = new ReferenceField<> (META, GitProjectsConfigRecord.META, "URI", ProjectId);

    public static final StringField IgnitionUser = new StringField(META, "IgnitionUser", SFieldFlags.SPRIMARY_KEY, SFieldFlags.SMANDATORY, SFieldFlags.SDESCRIPTIVE);
    public static final StringField SSHKey = new StringField(META, "SSHKey");

    public static final StringField UserName = new StringField(META, "UserName");
    public static final EncodedStringField Password = new EncodedStringField(META, "Password");

    static final Category UserProperties = new Category("GitReposUsersRecord.Category.UserProperties", 1000).include(ProjectName, IgnitionUser, SSHKey, UserName, Password);

    public int getId(){
        return this.getInt(Id);
    }
    public int getProjectId(){
        return this.getInt(ProjectId);
    }
    public String getUserName(){ return this.getString(UserName); }
    public String getIgnitionUser(){ return this.getString(IgnitionUser); }
    public String getProjectName(){ return this.getString(ProjectName); }
    public String getPassword(){ return this.getString(Password); }
    public String getSSHKey(){ return this.getString(SSHKey); }

    public void setProjectId(long projectId){ this.setLong(ProjectId, projectId); }

    static {
        ProjectName.getFormMeta().setEnabled(false);
        URI.getFormMeta().setVisible(false);

        IgnitionUser.getFormMeta().setFieldDescriptionKey("GitReposUsersRecord.IgnitionUser.Desc");
        IgnitionUser.getFormMeta().setFieldDescriptionKeyAddMode("GitReposUsersRecord.IgnitionUser.NewDesc");
        IgnitionUser.getFormMeta().setFieldDescriptionKeyEditMode("GitReposUsersRecord.IgnitionUser.EditDesc");

        ProjectName.getFormMeta().setFieldDescriptionKey("GitReposUsersRecord.ProjectName.Desc");
        ProjectName.getFormMeta().setFieldDescriptionKeyAddMode("GitReposUsersRecord.ProjectName.NewDesc");
        ProjectName.getFormMeta().setFieldDescriptionKeyEditMode("GitReposUsersRecord.ProjectName.EditDesc");

        SSHKey.getFormMeta().setEditorSource(new TextAreaEditorSource());
        SSHKey.getFormMeta().setFieldDescriptionKey("GitReposUsersRecord.SSHKey.Desc");
        SSHKey.getFormMeta().setFieldDescriptionKeyAddMode("GitReposUsersRecord.SSHKey.NewDesc");
        SSHKey.getFormMeta().setFieldDescriptionKeyEditMode("GitReposUsersRecord.SSHKey.EditDesc");
        SSHKey.setWide();

        UserName.getFormMeta().setFieldDescriptionKey("GitReposUsersRecord.UserName.Desc");
        UserName.getFormMeta().setFieldDescriptionKeyAddMode("GitReposUsersRecord.UserName.NewDesc");
        UserName.getFormMeta().setFieldDescriptionKeyEditMode("GitReposUsersRecord.UserName.EditDesc");

        Password.getFormMeta().setFieldDescriptionKey("GitReposUsersRecord.Password.Desc");
        Password.getFormMeta().setFieldDescriptionKeyAddMode("GitReposUsersRecord.Password.NewDesc");
        Password.getFormMeta().setFieldDescriptionKeyEditMode("GitReposUsersRecord.Password.EditDesc");
        Password.getFormMeta().setEditorSource(PasswordEditorSource.getSharedInstance());
    }
}

