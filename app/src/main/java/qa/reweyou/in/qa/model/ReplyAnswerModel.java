package qa.reweyou.in.qa.model;

/**
 * Created by master on 2/8/17.
 */

public class ReplyAnswerModel {

private String id;
private String queid;
private String ansid;
private String answer;
private String upvotes;
private String time;
private String views;
private String thumbnail;
private String uid;
    private String duration;
private String comments;
private String visibility;

    public String getDuration() {
        int time = Integer.parseInt(duration);
        int min = time / 60;
        int sec = time % 60;

        return String.format("%02d", min) + ":" + String.format("%02d", sec);
    }

    public String getThumbnail() {
        return thumbnail;
    }

    public String getTime() {
        return time;
    }

    public String getQueid() {
        return queid;
    }

    public String getUpvotes() {
        return upvotes;
    }

    public String getId() {
        return id;
    }

    public String getUid() {
        return uid;
    }

    public String getAnsid() {
        return ansid;
    }

    public String getAnswer() {
        return answer;
    }

    public String getComments() {
        return comments;
    }

    public String getViews() {
        return views;
    }

    public String getVisibility() {
        return visibility;
    }
}
