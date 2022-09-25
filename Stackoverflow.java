import java.util.*;

public class Stackoverflow {
    static HashMap<Question, Member> questionDB;
    public Stackoverflow(){

    }

    public static boolean addQuestionToDB(Question question, Member member)
    {
        questionDB.put(question, member);
        return true;
    }
}

class User{
    String name;
    String email;
    UserRole role;
}

enum UserRole{
    ADMIN, MEMBER, GUEST, SYSTEM;
}

enum UserStatus{
    ACTIVE, BLOCKED;
}

enum QuestionStatus{
    ACTIVE, CLOSED;
}

class Member extends User{

    String memberId;
    List<Question> postedQuestions;
    long reputation;
    UserStatus status;
    List<Badge> badges;
    
    public Member(String name, String email, UserRole role)
    {
        this.name = name;
        this.email = email;
        this.role = role;
        this.postedQuestions = new ArrayList<Question>();
        this.status = UserStatus.ACTIVE;
    }

    public boolean postQuestion(String id,String title, String description, List<String> questionTags, Member member)
    {
        if(member.role == UserRole.GUEST)
            return false;
        
        if(member.status != UserStatus.ACTIVE)
            return false;
        
        
        Question question = new Question(id, title, description, member, questionTags, new Date());
        Stackoverflow.addQuestionToDB(question, member);
        return true;
    }

    public boolean addComment(String id, String description, Member member, Question question)
    {
        if(question.status == QuestionStatus.CLOSED)
            return false;

        Comment comment = new Comment(id, description, member, question);
        question.addResponseToQuestion(comment);
        return true;
    }

    public boolean upVoteQuestion(Question question, Member member)
    {
        question.upVote(member);
        return true;
    }

    public boolean downVoteQuestion(Question question, Member member)
    {
        question.downVote(member);
        return true;
    }

    public long getReputation()
    {
        return this.reputation;
    }
}

class Moderator extends Member{

    public Moderator(String name, String email, UserRole role) {
        super(name, email, role);
    }

    public boolean closeQuestion(Question question){
        if(question.status == QuestionStatus.CLOSED) return false;

        question.status = QuestionStatus.CLOSED;
        return true;
    }
    public boolean restoreQuestion(Question question){
        if(question.status == QuestionStatus.ACTIVE) return false;

        question.status = QuestionStatus.ACTIVE;
        return true;
    }
}

class Question{
    String questionId;
    String title;
    String description;
    long upvotes;
    Member member;
    List<String> questionTags;
    List<Comment> questionResponses;
    Date created;
    QuestionStatus status;

    Set<Member> upVotedUsers;
    Set<Member> downVotedUsers;

    public Question(String id, String title, String description, Member member,List<String> tags, Date created)
    {
        this.questionId = id;
        this.title = title;
        this.description = description;
        this.member = member;
        this.questionTags.addAll(tags);
        this.created = created;
        this.status = QuestionStatus.ACTIVE;
    }

    public void addResponseToQuestion(Comment comment)
    {
        questionResponses.add(comment);
    }

    public boolean upVote(Member member)
    {
        if(this.upVotedUsers.contains(member))
            return false;

        if(this.downVotedUsers.contains(member))
            downVotedUsers.remove(member);

        this.upVotedUsers.add(member);
        this.upvotes++;
        return true;
    }

    public boolean downVote(Member member)
    {
        if(this.downVotedUsers.contains(member))
            return false;
        
        if(this.upVotedUsers.contains(member))
            upVotedUsers.remove(member);

        this.downVotedUsers.add(member);
        this.upvotes--;
        return true;
    }
}

class Answer{
    String answerId;
    String content;
    Member member;
    long upvotes;
    Question question;

    public Answer(String id, String content, Member member, Question question)
    {
        this.answerId = id;
        this.content = content;
        this.member = member;
        this.question = question;
        this.upvotes = 0;
    }

}
class Comment extends Answer{

    public Comment(String id, String content, Member member, Question question)
    {
        super(id, content, member, question);
    }
}


class Badge{
    String name;
    String description;

    public Badge(String name, String description)
    {
        this.name = name;
        this.description = description;
    }
}