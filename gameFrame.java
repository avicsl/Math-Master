
import javax.swing.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;
import javax.sound.sampled.*;
import java.io.File;
import java.io.IOException;
import java.util.LinkedList;
import java.util.Queue;

public class gameFrame extends javax.swing.JFrame {
    
    
    private Map<Integer, String> userAnswers = new HashMap<>();
    private Map<Integer, String> correctAnswers = new HashMap<>();
    private Map<Integer, String> questions = new HashMap<>();
    private int currentQuestionIndex = 0; // Track the current question index
    private Queue<Integer> pastCycleScores = new LinkedList<>();

    
    public static class Question {
        
        String question;
        String[] choices;
        String correctAnswer;

        public Question(String question, String[] choices, String correctAnswer) {
            this.question = question;
            this.choices = choices;
            this.correctAnswer = correctAnswer;
        }
    }
    
    
    
    public static class CircularLinkedList {
        private ArrayList<Question> questions = new ArrayList<>();
        private Random random = new Random();

        public void addQuestion(Question question) {
            questions.add(question);
        }

        public ArrayList<Question> getShuffledQuestions(int count) {
            Collections.shuffle(questions);
            return new ArrayList<>(questions.subList(0, Math.min(count, questions.size())));
        }
    }
    
    public void setSummaryData(Map<Integer, String> userAnswers, Map<Integer, String> correctAnswers, Map<Integer, String> questions) {
        this.userAnswers = userAnswers;
        this.correctAnswers = correctAnswers;
        this.questions = questions;
    }

    private CircularLinkedList questionList = new CircularLinkedList();
    private ArrayList<Question> currentCycleQuestions = new ArrayList<>();
    private Question currentQuestion;
    private int score = 0;
    private int questionCount = 0;
    private Timer timer;
    private int timeLeft = 10; // 10 seconds timer

    private Clip clip; 

    public gameFrame() {
         super ("Math Master");
        initComponents();
        this.setLocation(270, 120); // Set location after components are initialized
        this.setVisible(true); // Make sure the frame is visible
        loadQuestions(); // Load questions into the list
        startNewCycle(); // Start the first cycle
        playMusic("C:\\Users\\Alvhin\\Downloads\\bgm.wav");
    }

    private void playMusic(String musicFile) {
        try {
            File audioFile = new File(musicFile); // Path to the audio file
            AudioInputStream audioStream = AudioSystem.getAudioInputStream(audioFile);
            clip = AudioSystem.getClip(); // Initialize clip
            clip.open(audioStream);
            clip.start();
            clip.loop(Clip.LOOP_CONTINUOUSLY); // Loop the music continuously
        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
            e.printStackTrace();
        }
    }



    private void loadQuestions() {
    //Challenging Questions
    questionList.addQuestion(new Question("987 - 654 = ?", new String[]{"333", "324", "325", "326"}, "333"));
    questionList.addQuestion(new Question("123 x 19 = ?", new String[]{"2337", "2346", "2357", "2368"}, "2337"));
    questionList.addQuestion(new Question("144 / 8 = ?", new String[]{"17", "18", "19", "20"}, "18"));
    questionList.addQuestion(new Question("275 + 148 = ?", new String[]{"412", "413", "414", "423"}, "423"));
    questionList.addQuestion(new Question("567 x 23 = ?", new String[]{"13041", "13042", "13043", "13044"}, "13041"));
    questionList.addQuestion(new Question("982 - 749 = ?", new String[]{"231", "232", "233", "234"}, "233"));
    questionList.addQuestion(new Question("98 x 87 = ?", new String[]{"8506", "8507", "8508", "8509"}, "8506"));
    questionList.addQuestion(new Question("945 / 15 = ?", new String[]{"62", "63", "64", "65"}, "63"));
    questionList.addQuestion(new Question("239 + 468 = ?", new String[]{"705", "706", "707", "708"}, "707"));
    questionList.addQuestion(new Question("789 x 34 = ?", new String[]{"26706", "26707", "26708", "26709"}, "26706"));
    questionList.addQuestion(new Question("876 - 543 = ?", new String[]{"331", "332", "333", "334"}, "333"));
    questionList.addQuestion(new Question("134 x 22 = ?", new String[]{"2948", "2952", "2956", "2960"}, "2948"));
    questionList.addQuestion(new Question("384 / 16 = ?", new String[]{"23", "25", "24", "26"}, "24"));
    questionList.addQuestion(new Question("657 + 389 = ?", new String[]{"1045", "1047", "1046", "1048"}, "1046"));
    questionList.addQuestion(new Question("798 x 29 = ?", new String[]{"23142", "23143", "23144", "23145"}, "23142"));
    questionList.addQuestion(new Question("1098 - 763 = ?", new String[]{"333", "334", "335", "336"}, "335"));
    questionList.addQuestion(new Question("176 x 47 = ?", new String[]{"8272", "8274", "8276", "8278"}, "8272"));
    questionList.addQuestion(new Question("1296 / 12 = ?", new String[]{"106", "107", "108", "109"}, "108"));
    questionList.addQuestion(new Question("489 + 762 = ?", new String[]{"1249", "1250", "1252", "1251"}, "1251"));
    questionList.addQuestion(new Question("923 x 41 = ?", new String[]{"37843", "37844", "37845", "37846"}, "37843"));
    questionList.addQuestion(new Question("567 x 78 = ?", new String[]{"44226", "44250", "44235", "44229"}, "44226"));
    questionList.addQuestion(new Question("2345 - 1678 = ?", new String[]{"667", "668", "669", "670"}, "667"));
    questionList.addQuestion(new Question("652 x 54 = ?", new String[]{"35208", "35210", "35212", "35214"}, "35208"));
    questionList.addQuestion(new Question("1896 / 24 = ?", new String[]{"78", "79", "80", "81"}, "79"));
    questionList.addQuestion(new Question("999 + 1234 = ?", new String[]{"2231", "2232", "2233", "2234"}, "2233"));


    
    // Hard Questions
    questionList.addQuestion(new Question("123 + 77 = ?", new String[]{"200", "199", "198", "201"}, "200"));
    questionList.addQuestion(new Question("32 - 14 = ?", new String[]{"16", "17", "18", "19"}, "18"));
    questionList.addQuestion(new Question("27 x 4 = ?", new String[]{"108", "109", "110", "111"}, "108"));
    questionList.addQuestion(new Question("90 - 23 = ?", new String[]{"65", "66", "67", "68"}, "67"));
    questionList.addQuestion(new Question("84 / 7 = ?", new String[]{"11", "12", "13", "14"}, "12"));
    questionList.addQuestion(new Question("56 + 34 = ?", new String[]{"88", "89", "90", "91"}, "90"));
    questionList.addQuestion(new Question("72 - 18 = ?", new String[]{"52", "53", "55", "54"}, "54"));
    questionList.addQuestion(new Question("14 x 7 = ?", new String[]{"96", "98", "100", "102"}, "98"));
    questionList.addQuestion(new Question("81 / 9 = ?", new String[]{"8", "9", "10", "11"}, "9"));
    questionList.addQuestion(new Question("39 + 45 = ?", new String[]{"82", "83", "85", "84"}, "84"));
    questionList.addQuestion(new Question("64 / 8 = ?", new String[]{"8", "7", "9", "10"}, "8"));
    questionList.addQuestion(new Question("27 x 5 = ?", new String[]{"130", "135", "140", "145"}, "135"));
    questionList.addQuestion(new Question("72 / 8 = ?", new String[]{"8", "7", "10", "9"}, "9"));
    questionList.addQuestion(new Question("33 + 44 = ?", new String[]{"75", "76", "77", "78"}, "77"));
    questionList.addQuestion(new Question("15 x 6 = ?", new String[]{"85", "90", "95", "100"}, "90"));
    questionList.addQuestion(new Question("11 x 7 = ?", new String[]{"77", "75", "78", "79"}, "77"));
    questionList.addQuestion(new Question("28 + 32 = ?", new String[]{"60", "59", "61", "62"}, "60"));
    questionList.addQuestion(new Question("40 - 18 = ?", new String[]{"21", "22", "23", "24"}, "22"));
    questionList.addQuestion(new Question("27 / 9 = ?", new String[]{"2", "5", "4", "3"}, "3"));
    questionList.addQuestion(new Question("15 x 7 = ?", new String[]{"100", "115", "110", "105"}, "105"));
    questionList.addQuestion(new Question("54 - 29 = ?", new String[]{"25", "26", "26", "27"}, "25"));
    questionList.addQuestion(new Question("44 + 56 = ?", new String[]{"98", "99", "100", "101"}, "100"));
    questionList.addQuestion(new Question("18 x 5 = ?", new String[]{"90", "85", "95", "100"}, "90"));

    // Medium Questions
    questionList.addQuestion(new Question("67 + 24 = ?", new String[]{"89", "90", "91", "92"}, "91"));
    questionList.addQuestion(new Question("90 - 18 = ?", new String[]{"71", "72", "73", "74"}, "72"));
    questionList.addQuestion(new Question("27 x 3 = ?", new String[]{"78", "79", "80", "81"}, "81"));
    questionList.addQuestion(new Question("144 / 12 = ?", new String[]{"11", "12", "13", "14"}, "12"));
    questionList.addQuestion(new Question("85 + 47 = ?", new String[]{"131", "132", "133", "134"}, "132"));
    questionList.addQuestion(new Question("75 + 125 = ?", new String[]{"202", "201", "200", "203"}, "200"));
    questionList.addQuestion(new Question("280 - 95 = ?", new String[]{"188", "186", "187", "185"}, "185"));
    questionList.addQuestion(new Question("360 x 5 = ?", new String[]{"1790", "1791", "1792", "1800"}, "1800"));
    questionList.addQuestion(new Question("450 / 9 = ?", new String[]{"49", "51", "50", "52"}, "50"));
    questionList.addQuestion(new Question("220 + 170 = ?", new String[]{"398", "391", "392", "390"}, "390"));
    questionList.addQuestion(new Question("14 x 6 = ?", new String[]{"84", "82", "80", "86"}, "84"));
    questionList.addQuestion(new Question("18 / 3 = ?", new String[]{"5", "8", "7", "6"}, "6"));
    questionList.addQuestion(new Question("56 + 29 = ?", new String[]{"85", "84", "86", "87"}, "85"));
    questionList.addQuestion(new Question("102 - 45 = ?", new String[]{"55", "56", "57", "58"}, "57"));
    questionList.addQuestion(new Question("15 x 4 = ?", new String[]{"60", "61", "62", "63"}, "60"));
    questionList.addQuestion(new Question("168 / 8 = ?", new String[]{"20", "21", "22", "23"}, "21"));
    questionList.addQuestion(new Question("73 + 28 = ?", new String[]{"100", "101", "102", "103"}, "101"));
    questionList.addQuestion(new Question("200 - 76 = ?", new String[]{"123", "124", "125", "126"}, "124"));
    questionList.addQuestion(new Question("45 x 7 = ?", new String[]{"310", "325", "320", "315"}, "315"));
    questionList.addQuestion(new Question("300 / 12 = ?", new String[]{"23", "24", "25", "26"}, "25"));
    questionList.addQuestion(new Question("87 + 58 = ?", new String[]{"145", "144", "146", "147"}, "145"));
    questionList.addQuestion(new Question("72 - 39 = ?", new String[]{"32", "33", "34", "35"}, "33"));

    
    // Easy Questions
    questionList.addQuestion(new Question("12 + 15 = ?", new String[]{"25", "26", "27", "28"}, "27"));
    questionList.addQuestion(new Question("20 - 7 = ?", new String[]{"12", "13", "14", "15"}, "13"));
    questionList.addQuestion(new Question("8 x 7 = ?", new String[]{"54", "56", "58", "60"}, "56"));
    questionList.addQuestion(new Question("49 / 7 = ?", new String[]{"6", "9", "8", "7"}, "7"));
    questionList.addQuestion(new Question("9 + 16 = ?", new String[]{"24", "27", "26", "25"}, "25"));
    questionList.addQuestion(new Question("35 / 5 = ?", new String[]{"6", "5", "8", "7"}, "7"));
    
    }

    private void startNewCycle() {
    currentCycleQuestions = questionList.getShuffledQuestions(5); // Assuming 10 questions per cycle
    currentQuestionIndex = 0;
    questionCount = 0;
    score = 0;
    displayNextQuestion();
}

    
   
    private void displayNextQuestion() {
        if (questionCount >= currentCycleQuestions.size()) {
            // End of cycle
            if (clip != null) { // Stop music before opening the OptionFrame
                clip.stop();
            }
            transitionToOptionFrame();
        } else {
            currentQuestion = currentCycleQuestions.get(questionCount);
            jLabel1.setText(currentQuestion.question);
            jRadioButton1.setText(currentQuestion.choices[0]);
            jRadioButton2.setText(currentQuestion.choices[1]);
            jRadioButton3.setText(currentQuestion.choices[2]);
            jRadioButton4.setText(currentQuestion.choices[3]);

            buttonGroup1.clearSelection(); // Clear the selection for the next question


        // Reset timer
        timeLeft = 10;
        jLabel3.setText("Seconds left: " + timeLeft);
        if (timer != null) {
            timer.cancel();
        }
        timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                timeLeft--;
                jLabel3.setText("Seconds left: " + timeLeft);
                if (timeLeft <= 0) {
                    timer.cancel();
                    checkAnswer(null); // Time expired, consider as wrong answer
                }
            }
        }, 1000, 1000);
    }
    }
    
    private void checkAnswer(String selectedAnswer) {
        timer.cancel();
        userAnswers.put(currentQuestionIndex, selectedAnswer != null ? selectedAnswer : "No Answer");
        correctAnswers.put(currentQuestionIndex, currentQuestion.correctAnswer);
        questions.put(currentQuestionIndex, currentQuestion.question);

        if (selectedAnswer == null) {
            JOptionPane.showMessageDialog(this, "Time's up! Correct answer was: " + currentQuestion.correctAnswer);
        } else if (selectedAnswer.equals(currentQuestion.correctAnswer)) {
            score++;
            JOptionPane.showMessageDialog(this, "Correct!");
        } else {
            JOptionPane.showMessageDialog(this, "Wrong! Correct answer was: " + currentQuestion.correctAnswer);
        }

        currentQuestionIndex++;
        questionCount++;
        displayNextQuestion();
    }
    
    private void transitionToOptionFrame() {
    // Add the current score to the past cycles queue
    if (pastCycleScores.size() >= 5) {
        pastCycleScores.poll(); // Remove the oldest score
    }
    pastCycleScores.offer(score); // Add the new score
    
    optionFrame optionFrameInstance = new optionFrame(score, userAnswers, correctAnswers, questions);
    optionFrameInstance.setVisible(true);
    this.dispose(); 
}

    
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        buttonGroup1 = new javax.swing.ButtonGroup();
        buttonGroup2 = new javax.swing.ButtonGroup();
        jPanel1 = new javax.swing.JPanel();
        jRadioButton1 = new javax.swing.JRadioButton();
        jRadioButton2 = new javax.swing.JRadioButton();
        jRadioButton3 = new javax.swing.JRadioButton();
        jRadioButton4 = new javax.swing.JRadioButton();
        jLabel1 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jButton1 = new javax.swing.JButton();
        jButton6 = new javax.swing.JButton();
        jLabel2 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        buttonGroup1.add(jRadioButton1);
        jRadioButton1.setFont(new java.awt.Font("Stencil", 0, 24)); // NOI18N
        jRadioButton1.setForeground(new java.awt.Color(183, 5, 144));
        jRadioButton1.setText("jRadioButton1");
        jPanel1.add(jRadioButton1, new org.netbeans.lib.awtextra.AbsoluteConstraints(260, 280, 240, 40));

        buttonGroup1.add(jRadioButton2);
        jRadioButton2.setFont(new java.awt.Font("Stencil", 0, 24)); // NOI18N
        jRadioButton2.setForeground(new java.awt.Color(183, 5, 144));
        jRadioButton2.setText("jRadioButton2");
        jRadioButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jRadioButton2ActionPerformed(evt);
            }
        });
        jPanel1.add(jRadioButton2, new org.netbeans.lib.awtextra.AbsoluteConstraints(260, 350, 220, 30));

        buttonGroup1.add(jRadioButton3);
        jRadioButton3.setFont(new java.awt.Font("Stencil", 0, 24)); // NOI18N
        jRadioButton3.setForeground(new java.awt.Color(183, 5, 144));
        jRadioButton3.setText("jRadioButton3");
        jPanel1.add(jRadioButton3, new org.netbeans.lib.awtextra.AbsoluteConstraints(580, 290, 230, 20));

        buttonGroup1.add(jRadioButton4);
        jRadioButton4.setFont(new java.awt.Font("Stencil", 0, 24)); // NOI18N
        jRadioButton4.setForeground(new java.awt.Color(183, 5, 144));
        jRadioButton4.setText("jRadioButton4");
        jPanel1.add(jRadioButton4, new org.netbeans.lib.awtextra.AbsoluteConstraints(580, 350, 230, 30));

        jLabel1.setFont(new java.awt.Font("Showcard Gothic", 0, 40)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(112, 22, 109));
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("Question");
        jPanel1.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(270, 120, 420, -1));

        jLabel3.setFont(new java.awt.Font("ROG Fonts", 0, 24)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(187, 34, 124));
        jLabel3.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jPanel1.add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(260, 30, 450, 40));

        jButton1.setBackground(new java.awt.Color(219, 60, 154));
        jButton1.setFont(new java.awt.Font("ROG Fonts", 0, 14)); // NOI18N
        jButton1.setForeground(new java.awt.Color(112, 22, 109));
        jButton1.setText("SUBMIT");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });
        jPanel1.add(jButton1, new org.netbeans.lib.awtextra.AbsoluteConstraints(420, 480, 130, 40));

        jButton6.setBackground(new java.awt.Color(255, 102, 204));
        jButton6.setFont(new java.awt.Font("ROG Fonts", 0, 10)); // NOI18N
        jButton6.setForeground(new java.awt.Color(112, 22, 109));
        jButton6.setText("<  EXIT");
        jButton6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton6ActionPerformed(evt);
            }
        });
        jPanel1.add(jButton6, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 508, 80, -1));

        jLabel2.setIcon(new javax.swing.ImageIcon("C:\\Users\\Alvhin\\Downloads\\PIXEL BACKGROUND (6).png")); // NOI18N
        jLabel2.setText("jLabel2");
        jPanel1.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 960, -1));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    
    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
    if (jRadioButton1.isSelected()) {
            checkAnswer(jRadioButton1.getText());
        } else if (jRadioButton2.isSelected()) {
            checkAnswer(jRadioButton2.getText());
        } else if (jRadioButton3.isSelected()) {
            checkAnswer(jRadioButton3.getText());
        } else if (jRadioButton4.isSelected()) {
            checkAnswer(jRadioButton4.getText());
        } else {
            JOptionPane.showMessageDialog(this, "Please select an answer.");
        }
    
    buttonGroup1.add(jRadioButton1);
    buttonGroup1.add(jRadioButton2);
    buttonGroup1.add(jRadioButton3);
    buttonGroup1.add(jRadioButton4);
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jRadioButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jRadioButton2ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jRadioButton2ActionPerformed

    private void jButton6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton6ActionPerformed
if (clip != null) { // Stop music before opening the HelpFrame
            clip.stop();
        } 
if (timer != null) {
        timer.cancel();
    }


        Opening Opening = new Opening(); // Assuming GameFrame class exists
        Opening.setVisible(true);
        this.dispose();
    }//GEN-LAST:event_jButton6ActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.ButtonGroup buttonGroup2;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton6;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JRadioButton jRadioButton1;
    private javax.swing.JRadioButton jRadioButton2;
    private javax.swing.JRadioButton jRadioButton3;
    private javax.swing.JRadioButton jRadioButton4;
    // End of variables declaration//GEN-END:variables
}