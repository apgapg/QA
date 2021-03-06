package qa.reweyou.in.qa.model;

/**
 * Created by master on 24/2/17.
 */

public class CommentModel {

    private String username;
    private String comment;
    private String commentid;
    private String imageurl;
    private String time;
    private String badge;
    private String points;
    private String uid;
    private String upvotes;
    private String status = "false";
    private String tags = "";
private String queid;
private String ansid;

    public String getAnsid() {
        return ansid;
    }

    public String getQueid() {
        return queid;
    }

    public String getTags() {
        return tags;
    }
    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getUpvotes() {
        return upvotes;
    }

    public void setUpvotes(String upvotes) {
        this.upvotes = upvotes;
    }

    public String getUid() {
        return uid;
    }

    public String getBadge() {
        return badge;
    }

    public String getPoints() {
        return points;
    }

    public String getTime() {
        return time;
    }

    public String getImageurl() {
        return imageurl;
    }

    public String getUsername() {
        return username;
    }

    public String getComment() {
        return comment;
    }

    public String getCommentid() {
        return commentid;
    }
}
