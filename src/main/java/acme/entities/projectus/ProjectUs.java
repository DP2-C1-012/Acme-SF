
package acme.entities.projectus;

import javax.persistence.Entity;
import javax.persistence.Index;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import acme.client.data.AbstractEntity;
import acme.entities.projects.Project;
import acme.entities.userstory.UserStory;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(indexes = {
	@Index(columnList = "project_id"), @Index(columnList = "project_id,user_story_id"), @Index(columnList = "user_story_id")
})
public class ProjectUs extends AbstractEntity {

	private static final long	serialVersionUID	= 1L;

	@NotNull
	@Valid
	@ManyToOne(optional = false)
	private Project				project;

	@NotNull
	@Valid
	@ManyToOne(optional = false)
	private UserStory			userStory;
}
