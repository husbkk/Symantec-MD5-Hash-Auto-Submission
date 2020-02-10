
import com.gargoylesoftware.htmlunit.BrowserVersion;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.datatransfer.StringSelection;
import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;


class MyFrame
        extends JFrame
        implements ActionListener {

    private Container c;
    private JLabel title;
    private JLabel fname;
    private JTextField tfname;
    private JLabel lname;
    private JTextField tlname;
    private JLabel cname;
    private JTextField tcname;
    private JLabel email;
    private JTextField temail;
    private JLabel sid;
    private JTextField tsid;
    private JLabel severity;
    private JCheckBox tseverity;
    private JButton copy;
    private JButton sub;
    private JButton reset;
    private JTextArea tout;
    private JTextArea type;
    private String[] allResult = null;
    private JProgressBar progressBar;
    private int sentCount = 0;

    public MyFrame() {
        setTitle("MD5 Auto Submission Bot");
        setBounds(300, 90, 680, 460);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setResizable(false);

        c = getContentPane();
        c.setLayout(null);

        title = new JLabel("Sender Information");
        title.setFont(new Font("Arial", Font.PLAIN, 17));
        title.setSize(300, 17);
        title.setLocation(100, 20);
        c.add(title);

        fname = new JLabel("First Name");
        fname.setFont(new Font("Arial", Font.PLAIN, 15));
        fname.setSize(100, 20);
        fname.setLocation(30, 50);
        c.add(fname);

        tfname = new JTextField();
        tfname.setFont(new Font("Arial", Font.PLAIN, 13));
        tfname.setSize(203, 22);
        tfname.setLocation(130, 50);
        c.add(tfname);

        lname = new JLabel("Last Name");
        lname.setFont(new Font("Arial", Font.PLAIN, 15));
        lname.setSize(100, 20);
        lname.setLocation(30, 80);
        c.add(lname);

        tlname = new JTextField();
        tlname.setFont(new Font("Arial", Font.PLAIN, 13));
        tlname.setSize(203, 22);
        tlname.setLocation(130, 80);
        c.add(tlname);

        cname = new JLabel("Company");
        cname.setFont(new Font("Arial", Font.PLAIN, 15));
        cname.setSize(100, 20);
        cname.setLocation(30, 110);
        c.add(cname);

        tcname = new JTextField();
        tcname.setFont(new Font("Arial", Font.PLAIN, 13));
        tcname.setSize(203, 22);
        tcname.setLocation(130, 110);
        tcname.setText("Hong Kong Bank of East Asia");
        c.add(tcname);

        email = new JLabel("Email");
        email.setFont(new Font("Arial", Font.PLAIN, 15));
        email.setSize(100, 20);
        email.setLocation(30, 140);
        c.add(email);

        temail = new JTextField();
        temail.setFont(new Font("Arial", Font.PLAIN, 13));
        temail.setSize(203, 22);
        temail.setLocation(130, 140);
        c.add(temail);

        sid = new JLabel("Support ID");
        sid.setFont(new Font("Arial", Font.PLAIN, 15));
        sid.setSize(100, 20);
        sid.setLocation(30, 170);
        c.add(sid);

        tsid = new JTextField();
        tsid.setFont(new Font("Arial", Font.PLAIN, 13));
        tsid.setSize(203, 22);
        tsid.setLocation(130, 170);
        c.add(tsid);

        severity = new JLabel("High Severity");
        severity.setFont(new Font("Arial", Font.PLAIN, 15));
        severity.setSize(100, 20);
        severity.setLocation(30, 200);
        c.add(severity);

        tseverity = new JCheckBox();
        tseverity.setFont(new Font("Arial", Font.PLAIN, 15));
        tseverity.setSize(23, 20);
        tseverity.setLocation(130, 200);
        c.add(tseverity);

        copy = new JButton("Copy Result");
        copy.setFont(new Font("Arial", Font.PLAIN, 14));
        copy.setSize(145, 20);
        copy.setLocation(185, 200);
        copy.addActionListener(this);
        copy.setVisible(false);
        c.add(copy);

        sub = new JButton("Submit");
        sub.setFont(new Font("Arial", Font.PLAIN, 14));
        sub.setSize(145, 20);
        sub.setLocation(30, 230);
        sub.addActionListener(this);
        c.add(sub);

        reset = new JButton("Reset");
        reset.setFont(new Font("Arial", Font.PLAIN, 14));
        reset.setSize(145, 20);
        reset.setLocation(185, 230);
        reset.addActionListener(this);
        c.add(reset);


        tout = new JTextArea();
        tout.setFont(new Font("Arial", Font.PLAIN, 15));
        tout.setLineWrap(true);
        tout.setEditable(false);
        JScrollPane outputScroll = new JScrollPane(tout);
        outputScroll.setBounds(350,50, 300, 370);   //350,20,300,400
        outputScroll.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
//        tout.setSize(300, 400);
//        tout.setLocation(500, 100);
//        tout.setLineWrap(true);
//        tout.setEditable(false);
        c.add(outputScroll);


        type = new JTextArea();
        type.setFont(new Font("Arial", Font.PLAIN, 14));
        type.setLineWrap(true);
//        type.setSize(200,100);
//        type.setLocation(30,300);
        JScrollPane inputScroll = new JScrollPane(type);
        inputScroll.setBounds(30, 260, 301, 160);       //switch line would go double for width 300.
        inputScroll.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        c.add(inputScroll);

//        JLabel author = new JLabel("Written by Kao");
//        author.setFont(new Font("Arial", Font.PLAIN, 10));
//        author.setSize(100, 12);
//        author.setLocation(582, 423);
//        c.add(author);

        progressBar = new JProgressBar();
        progressBar.setValue(0);
        progressBar.setStringPainted(true);
        progressBar.setBounds(350,20,300,20);

//        c.add(progressBar);

        setVisible(true);
    }

    public Boolean formIsValid() {
        String email = temail.getText();
        boolean emailIsValid = (email.contains("@") && email.contains(".") && !email.contains(" ")) ? true : false;
        if (!tfname.getText().isEmpty()
                && !tlname.getText().isEmpty()
                && !tcname.getText().isEmpty()
                && !tsid.getText().isEmpty()
                && !type.getText().isEmpty()
                && emailIsValid)
            return true;
        return false;
    }

    public String getFormInfo() {
        boolean checkSeverity = (tseverity.isSelected()) ? true : false;
        String data
                = "First Name: " + tfname.getText() + "\n"
                + "Last Name: " + tlname.getText() + "\n"
                + "Company: " + tcname.getText() + "\n"
                + "Email: " + temail.getText() + "\n"
                + "Support ID: " + tsid.getText() + "\n"
                + "High Severity: " + checkSeverity + "\n";
        return data;
    }

    public String getAllTrackingNum(String[] arr) {
        String data = "";
        for (String tn: arr)
            if (tn != null)
                data += tn + "\n";
        return data;
    }

// start optional merge
    public HtmlPage retrievePage(String url) {
        WebClient webclient = new WebClient(BrowserVersion.CHROME);

        webclient.getOptions().setRedirectEnabled(true);
        webclient.getOptions().setCssEnabled(false);
        webclient.getOptions().setThrowExceptionOnScriptError(false);
        webclient.getOptions().setThrowExceptionOnFailingStatusCode(false);
        webclient.getOptions().setUseInsecureSSL(true);
        webclient.getOptions().setJavaScriptEnabled(false);
        webclient.getCookieManager().setCookiesEnabled(true);

        HtmlPage htmlpage = null;
        try {
            htmlpage = webclient.getPage(url);
            webclient.waitForBackgroundJavaScript(10000);
        } catch (Exception e1) {
            e1.printStackTrace();
        }
        return htmlpage;
    }

    public void changeFieldStatus(Boolean status) {
            tfname.setEditable(status);
            tlname.setEditable(status);
            tcname.setEditable(status);
            temail.setEditable(status);
            tsid.setEditable(status);
            tseverity.setEnabled(status);
            type.setEditable(status);
    }

    public void resultToClipboard(String[] arr) {
        StringSelection allTrackNum = new StringSelection(getAllTrackingNum(arr));
        Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
        clipboard.setContents(allTrackNum, null);
    }

    public void reset() {
        String clear = "";
        tfname.setText(clear);
        temail.setText(clear);
        tlname.setText(clear);
        tsid.setText(clear);
        type.setText(clear);
        type.setEditable(true);
        tout.setText(clear);
        tseverity.setSelected(false);
    }

    public int getSentCount() {
        return sentCount;
    }

    public void initiateProgress() {
        while (sentCount != 100) {
            int sent = getSentCount();
            progressBar.setValue(sent);

        }
    }

    public void actionPerformed(ActionEvent event) {
        String typeInput = type.getText();
        String[] hashList = typeInput.split("\\n");
        String[] trackingNumber = new String[hashList.length];
        String nextPageContent;

        if (event.getSource() == sub) {
            changeFieldStatus(false);
//            initiateProgress();

            //TESTING

            //

            if (formIsValid()) {
//                System.out.println("Programme Running. Please Wait.");

                for (int i = 0; i < hashList.length; i++) {
//                    System.out.println(getFormInfo());

                    HtmlPage htmlPage = retrievePage("https://submit.symantec.com/websubmit/bcs.cgi");

                    //Get HTML elements
                    final HtmlForm form = htmlPage.getFormByName("appform");
                    final HtmlTextInput _firstName = form.getInputByName("fname");
                    final HtmlTextInput _lastName = form.getInputByName("lname");
                    final HtmlTextInput _companyName = form.getInputByName("cname");
                    final HtmlTextInput _email = form.getInputByName("email");
                    final HtmlTextInput _email2 = form.getInputByName("email2");        //confirmation email
                    final HtmlTextInput _pin = form.getInputByName("pin");
                    final HtmlSelect _stype = form.getSelectByName("stype");
                    final HtmlTextInput _hash = form.getInputByName("hash");
                    final HtmlCheckBoxInput _severity = form.getInputByName("critical");
                    final HtmlSubmitInput submit = form.getInputByValue("Submit");

                    //Set value to fields of website accordingly
                    _firstName.setValueAttribute(tfname.getText());
                    _lastName.setValueAttribute(tlname.getText());
                    _companyName.setValueAttribute(tcname.getText());
                    _email.setValueAttribute(temail.getText());
                    _email2.setValueAttribute(temail.getText());
                    _pin.setValueAttribute(tsid.getText());
                    _stype.setSelectedAttribute("hash", true);
                    _hash.setValueAttribute(hashList[i]);                    //testing hash: E28ADC9EA142DB02FC2978D3D42E2F2F
                    if (tseverity.isSelected()) { _severity.setAttribute("checked", "checked"); }


                    /*Test if the website has received correct form value
                    System.out.println(_firstName.getValueAttribute());
                    System.out.println(_lastName.getValueAttribute());
                    System.out.println(_companyName.getValueAttribute());
                    System.out.println(_email.getValueAttribute());
                    System.out.println(_pin.getValueAttribute());
                    System.out.println(_stype.getSelectedOptions());
                    System.out.println(_severity.getValueAttribute());
                    System.out.println(_hash.getValueAttribute());
                    */

                    //Submit Form and retrieve info
                    HtmlPage nextPage = null;
                    try {
                        nextPage = submit.click();
                        nextPageContent = nextPage.asXml();
                        String[] htmlStr = nextPageContent.split("Tracking Number: ");
                        String[] htmlStr2 = htmlStr[1].split("\n" +
                                "          </b>");
                        trackingNumber[i] = htmlStr2[0];
//                        tout.setText(getFormInfo() + "Tracking number(s): \n" + getAllTrackingNum(trackingNumber));
                        tout.setText("Tracking number(s): \n" + getAllTrackingNum(trackingNumber));
                        //                        System.out.println("Tracking Number: " + trackingNumber[i]);

                        sentCount++;
                    } catch (Exception e2) {
                        e2.printStackTrace();
                        JOptionPane.showMessageDialog(c, "Error occurred: invalid information.");
                    }
                }
                if (trackingNumber[0] != null) {
                    allResult = trackingNumber;
//                    System.out.println(getAllTrackingNum(trackingNumber));
                    resultToClipboard(trackingNumber);
                    JOptionPane.showMessageDialog(c, "Completed. \nAll tracking numbers are copied.");
                    copy.setVisible(true);
                }
            }
            else {
                tout.setText("");
                JOptionPane.showMessageDialog(c,"Error occurred: incorrect information.", "Error", JOptionPane.WARNING_MESSAGE);
            }
            changeFieldStatus(true);
        }
        else if (event.getSource() == reset) {
            reset();
//            JFrame newFrame = new JFrame("New Window");
//            newFrame.pack();
//            newFrame.setVisible(true);
//            newFrame.setSize(500,500);
//            newFrame.setLocationRelativeTo(null);
        }
        else if (event.getSource() == copy) {
            resultToClipboard(allResult);
            JOptionPane.showMessageDialog(c, "Tracking Numbers are copied.");
        }
    }
}

// Driver Code
class GUI {
    public static void main(String[] args) throws Exception {
        MyFrame f = new MyFrame();
    }
}