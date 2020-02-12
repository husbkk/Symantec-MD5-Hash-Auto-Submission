import com.gargoylesoftware.htmlunit.BrowserVersion;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.datatransfer.StringSelection;
import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;


class MyFrame extends JFrame implements ActionListener {

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
    private JButton setHash;
    private JTextArea tout;
    private String[] allResult = null;
    private String[] hashList = null;
    private HashImporter hashImporter;

    public MyFrame() {
        setTitle("MD5 Auto Submission Bot");
        setBounds(300, 90, 680, 330);
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

        setHash = new JButton("Import Hash");
        setHash.setFont(new Font("Arial", Font.PLAIN, 14));
        setHash.setSize(145, 20);
        setHash.setLocation(30, 240);
        setHash.addActionListener(this);
        c.add(setHash);

        copy = new JButton("Copy Result");
        copy.setFont(new Font("Arial", Font.PLAIN, 14));
        copy.setSize(145, 20);
        copy.setLocation(185, 240);
        copy.addActionListener(this);
        copy.setEnabled(false);
        c.add(copy);

        sub = new JButton("Submit");
        sub.setFont(new Font("Arial", Font.PLAIN, 14));
        sub.setSize(145, 20);
        sub.setLocation(30, 270);
        sub.addActionListener(this);
        c.add(sub);

        reset = new JButton("Reset");
        reset.setFont(new Font("Arial", Font.PLAIN, 14));
        reset.setSize(145, 20);
        reset.setLocation(185, 270);
        reset.addActionListener(this);
        c.add(reset);

        tout = new JTextArea();
        tout.setFont(new Font("Arial", Font.PLAIN, 15));
        tout.setLineWrap(true);
        tout.setEditable(false);
        JScrollPane outputScroll = new JScrollPane(tout);
        outputScroll.setBounds(350,20, 300, 273);
        outputScroll.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        c.add(outputScroll);

        hashImporter = new HashImporter();
        setVisible(true);
    }

    public Boolean isValidForm() {
        String email = temail.getText();
        boolean emailIsValid = (email.contains("@") && email.contains(".") && !email.contains(" ")) ? true : false;
        if (!tfname.getText().isEmpty()
                && !tlname.getText().isEmpty()
                && !tcname.getText().isEmpty()
                && !tsid.getText().isEmpty()
                && hashList[0] != null
                && emailIsValid)
            return true;
        return false;
    }

    /*
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
    */

    public String getAllTrackingNum(String[] arr) {
        String data = "";
        for (String tn: arr)
            if (tn != null)
                data += tn + "\n";
        return data;
    }

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
        tout.setText(clear);
        tseverity.setSelected(false);
        hashImporter.reset();
    }

    public void actionPerformed(ActionEvent event) {
        String[] trackingNumber;

        String nextPageContent;

        if (event.getSource() == sub) {
            //TESTING
//            long startTime = System.nanoTime();
            //
            changeFieldStatus(false);


            hashList = hashImporter.getHash();

            if (isValidForm()) {
//                    System.out.println(getFormInfo());

                trackingNumber =  new String[hashList.length];
                final HtmlPage HTML_PAGE = retrievePage("https://submit.symantec.com/websubmit/bcs.cgi");

                //Get HTML elements
                final HtmlForm FORM = HTML_PAGE.getFormByName("appform");
                final HtmlTextInput FIRST_NAME = FORM.getInputByName("fname");
                final HtmlTextInput LAST_NAME = FORM.getInputByName("lname");
                final HtmlTextInput COMPANY_NAME = FORM.getInputByName("cname");
                final HtmlTextInput EMAIL = FORM.getInputByName("email");
                final HtmlTextInput EMAIL2 = FORM.getInputByName("email2");        //confirmation email
                final HtmlTextInput PIN = FORM.getInputByName("pin");
                final HtmlSelect TYPE = FORM.getSelectByName("stype");
                final HtmlTextInput HASH = FORM.getInputByName("hash");
                final HtmlCheckBoxInput SEVERITY = FORM.getInputByName("critical");
                final HtmlSubmitInput SUBMIT = FORM.getInputByValue("Submit");

                //Set value to fields of website accordingly
                FIRST_NAME.setValueAttribute(tfname.getText());
                LAST_NAME.setValueAttribute(tlname.getText());
                COMPANY_NAME.setValueAttribute(tcname.getText());
                EMAIL.setValueAttribute(temail.getText());
                EMAIL2.setValueAttribute(temail.getText());
                PIN.setValueAttribute(tsid.getText());
                if (tseverity.isSelected()) {
                    SEVERITY.setAttribute("checked", "checked");
                }
                TYPE.setSelectedAttribute("hash", true);
                for (int i = 0; i < hashList.length; i++) {
                    HASH.setValueAttribute(hashList[i]);                    //testing hash: E28ADC9EA142DB02FC2978D3D42E2F2F


                    /*Test if the website has received correct form value
                    System.out.println(FIRST_NAME.getValueAttribute());
                    System.out.println(LAST_NAME.getValueAttribute());
                    System.out.println(COMPANY_NAME.getValueAttribute());
                    System.out.println(EMAIL.getValueAttribute());
                    System.out.println(PIN.getValueAttribute());
                    System.out.println(TYPE.getSelectedOptions());
                    System.out.println(SEVERITY.getValueAttribute());
                    System.out.println(HASH.getValueAttribute());
                    */

                    //Submit Form and retrieve info
                    HtmlPage nextPage = null;
                    try {
                        nextPage = SUBMIT.click();
                        nextPageContent = nextPage.asXml();
                        String[] htmlStr = nextPageContent.split("Tracking Number: ");
                        String[] htmlStr2 = htmlStr[1].split("\n" +
                                "          </b>");
                        trackingNumber[i] = htmlStr2[0];
                    } catch (Exception e2) {
                        e2.printStackTrace();
                        JOptionPane.showMessageDialog(c, "Error: invalid information.");
                    }

//                    long endTime = System.nanoTime();
//                    System.out.println((endTime - startTime)/1000000000.0);
                }
                if (trackingNumber[0] != null) {
                    allResult = trackingNumber;
//                    System.out.println(getAllTrackingNum(trackingNumber));
                    resultToClipboard(trackingNumber);
                    tout.setText("Tracking number(s): \n" + getAllTrackingNum(trackingNumber));
                    copy.setEnabled(true);
                    JOptionPane.showMessageDialog(c, "Completed. \nAll tracking numbers are copied.");
                }
            } else {
                tout.setText("");
                JOptionPane.showMessageDialog(c, "Error: incorrect information.", "Error", JOptionPane.WARNING_MESSAGE);
            }
            changeFieldStatus(true);
        } else if (event.getSource() == reset) {
            reset();
        } else if (event.getSource() == copy) {
            resultToClipboard(allResult);
            JOptionPane.showMessageDialog(c, "Tracking Numbers are copied.");
        } else if (event.getSource() == setHash) {
            hashImporter.setVisible(true);
        }
    }
}

class HashImporter extends JFrame implements ActionListener{

    private Container c;
    private JTextArea type;
    private JLabel title;
    private JButton set;
    private JButton cancel;
    private String[] allInputs;
    private String[] hash;
    private Boolean isCorrectHash = false;
    private String savedData;

    public HashImporter() {
        setSize(350,530);
        setLocationRelativeTo(null);
        setResizable(true);

        c = getContentPane();
        c.setLayout(null);

        title = new JLabel("Enter one MD5 hash per line:");
        title.setFont(new Font("Arial", Font.PLAIN, 15));
        title.setSize(300, 15);
        title.setLocation(12, 15);
        c.add(title);

        type = new JTextArea();
        type.setFont(new Font("Arial", Font.PLAIN, 14));
        type.setLineWrap(true);
        JScrollPane inputScroll = new JScrollPane(type);
        inputScroll.setBounds(10, 40, 330, 430);
        inputScroll.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        c.add(inputScroll);

        set = new JButton("Import");
        set.setFont(new Font("Arial", Font.PLAIN, 14));
        set.setBounds(10, 480,160, 20);
        set.addActionListener(this);
        c.add(set);

        cancel = new JButton("Cancel");
        cancel.setFont(new Font("Arial", Font.PLAIN, 14));
        cancel.setBounds(180, 480,160, 20);
        cancel.addActionListener(this);
        c.add(cancel);
    }

    public void reset() {
        String clear = "";
        type.setText(clear);
        type.setEditable(true);
    }

    public String[] getHash() {
        return hash;
    }

    public void actionPerformed(ActionEvent event){
        if (event.getSource() == set) {
            String typeInput = type.getText();
            allInputs = typeInput.split("\\n");

            for (String s: allInputs) {
                s = s.trim();
                isCorrectHash = (s.matches("[a-zA-Z0-9]*") && s.length() == 32) ? true : false;
                if (isCorrectHash == false) {
                    break;
                }
            }

            if (isCorrectHash) {
                hash = new String[allInputs.length];
                for (int i=0; i < allInputs.length; i++) {
                    hash[i] = allInputs[i].trim();
                }
                allInputs = null;
                savedData = type.getText();
                dispose();
            }
            else {
                JOptionPane.showMessageDialog(c, "Error: Incorrect MD5 format.", "Error", JOptionPane.WARNING_MESSAGE);
            }
        }
        else if (event.getSource() == cancel){
            dispose();
            type.setText(savedData);
        }
    }
}

// Driver Code
class GUI {
    public static void main(String[] args) throws Exception {
        MyFrame f = new MyFrame();
    }
}
