package pl.edu.agh.model.loans;

import jakarta.persistence.*;
import pl.edu.agh.model.books.CoverType;
import pl.edu.agh.model.books.Title;
import pl.edu.agh.model.users.Member;

import java.util.Date;

@Entity
public class Loan {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int loanId;

    @Temporal(TemporalType.DATE)
    private Date startLoanDate;
    @Temporal(TemporalType.DATE)
    private Date endLoanDate;
    @Enumerated(EnumType.STRING)
    private CoverType coverType;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", referencedColumnName = "userId")
    private Member member;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="title_id", referencedColumnName = "titleId")
    private Title title;


    public Loan(Date startLoanDate, Date endLoanDate, Member member, Title title, CoverType coverType) {
        this.startLoanDate = startLoanDate;
        this.endLoanDate = endLoanDate;
        this.member = member;
        this.title = title;
        this.coverType = coverType;
    }

    public Loan() {
    }

    public int getLoanId() {
        return loanId;
    }

    public void setLoanId(int loanId) {
        this.loanId = loanId;
    }

    public Date getStartLoanDate() {
        return startLoanDate;
    }

    public void setStartLoanDate(Date startLoanDate) {
        this.startLoanDate = startLoanDate;
    }

    public Date getEndLoanDate() {
        return endLoanDate;
    }

    public void setEndLoanDate(Date endLoanDate) {
        this.endLoanDate = endLoanDate;
    }

    public Member getMember() {
        return member;
    }

    public void setMember(Member member) {
        this.member = member;
    }

    public Title getTitle() {
        return title;
    }

    public void setTitle(Title title) {
        this.title = title;
    }

    public CoverType getCoverType() {
        return coverType;
    }

    public void setCoverType(CoverType coverType) {
        this.coverType = coverType;
    }
}
