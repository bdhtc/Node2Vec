package czzUI;

import czz2D.CoordinateSystem;
import czz2D.Point;
import czz2D.PointSet;
import czzClusterAnalysis.Cluster;
import czzClusterAnalysis.ClusterNode;
import czzClusterAnalysis.KMeans;
import czzLog.Log;
import czzMatrix.Matrix;
import czzNode2Vec.Graph4N2V;
import czzNode2Vec.Node2Vec;
import czzVector.CVector;
import czzVector.IVector;
import czzWord2Vec.Word2Vec;
import czzWord2Vec.Word2Vec.ModelType;
import czzWord2Vec.Word2Vec.TrainMethod;
import lombok.SneakyThrows;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * 用户界面
 *
 * @author CZZ
 */
public class UIFrame extends JFrame {

    /**
     * 序列号
     */
    private static final long serialVersionUID = 1L;

    /**
     * 画布
     */
    private JPanel canvas;

    /**
     * 坐标系
     */
    private CoordinateSystem cs;            //TODO:使用监听者模式重构操作逻辑

    /**
     * 图
     */
    Graph4N2V<Integer> Graph;

    /**
     * Node2Vec
     */
    Node2Vec n2v;

    /**
     * Word2Vec
     */
    Word2Vec<String> w2v;

    /**
     * 模型词向量对应的词
     */
    Integer[] modelsName;

    /**
     * 嵌入向量数组
     */
    IVector[] models;

    /**
     * 聚类数
     */
    int kCategories;

    /**
     * 聚类分析
     */
    Cluster<Integer> cluster;

    /**
     * 聚类分析结果
     */
    ArrayList<ClusterNode<Integer>> clusterResult;

    /**
     * 降维结果
     */
    Matrix pca;

    /*================================方法 methods================================*/

    /**
     * 构造方法
     */
    public UIFrame() {
        super("图聚类分析");
        setExtendedState(JFrame.MAXIMIZED_BOTH);                //�?大化
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);            //关闭按钮
        this.setLayout(new BorderLayout());
        initMenu();                        //设置菜单栏，添加菜单�?
        initToolBar();
        initCanvas();
    }

    /**
     * 设置坐标系
     */
    public void setCoordinateSystem(CoordinateSystem cs) {
        this.cs = cs;
    }

    /**
     * 初始化菜单
     */
    @SuppressWarnings("deprecation")
    private void initMenu() {
        MenuBar menuBar = new MenuBar();
        this.setMenuBar(menuBar);
        Menu fileMenu = new Menu("文件");
        menuBar.add(fileMenu);
        MenuItem loadGraphFile = new MenuItem("加载图");
        MenuItem loadNodeSequence = new MenuItem("加载遍历序列");
        loadNodeSequence.disable();
        MenuItem loadWordVector = new MenuItem("加载词向量");
        loadWordVector.disable();
        MenuItem loadPoints = new MenuItem("加载点集合");
        loadPoints.disable();
        fileMenu.add(loadGraphFile);
        fileMenu.add(loadNodeSequence);
        fileMenu.add(loadWordVector);
        fileMenu.add(loadPoints);
        fileMenu.addSeparator();
        MenuItem exitMenu = new MenuItem("退出");
        fileMenu.add(exitMenu);

        ActionListener menuListener = e -> {
            String cmd = e.getActionCommand();
            if (cmd.equals("退出")) {
                int option = JOptionPane.showConfirmDialog(rootPane, "确定退出？", "退出", JOptionPane.YES_NO_OPTION);
                if (option == JOptionPane.YES_OPTION) System.exit(0);
            } else if (cmd.equals("加载图")) {
            	/*
            	FileDialog fileLoadDialog = new FileDialog(this,"load file",FileDialog.LOAD);
            	fileLoadDialog.setVisible(true);
            	System.out.println(fileLoadDialog.getDirectory() + fileLoadDialog.getFile());
            	*/
                JFileChooser fc = new JFileChooser();
                fc.addChoosableFileFilter(new FileNameExtensionFilter("邻接表文件(*.edgelist)", "edgelist"));
                int returnVal = fc.showOpenDialog(this);        //0选择1取消
                File file = fc.getSelectedFile();
                if (file != null && returnVal != 1) {                    //选择文件
                    System.out.println(returnVal);
                    System.out.println(file.getPath() + file.getName());
                } else {
                    System.out.println(returnVal);
                }
            }
        };

        fileMenu.addActionListener(menuListener);
    }

    /**
     * 初始化工具栏
     */
    private void initToolBar() {
        JToolBar toolBar = new JToolBar();//实例化工具条
        this.add(toolBar, BorderLayout.NORTH);
        JButton selectGraphFileButton = new JButton("选择文件");
        JButton loadGraphButton = new JButton("加载图");
        JButton traverseGraphButton = new JButton("遍历图");
        JButton embeddingButton = new JButton("嵌入向量");
        JButton clusterButton = new JButton("聚类分析");
        JButton dimensionReductionButton = new JButton("降维分析");
        JButton paintButton = new JButton("画图");
        JButton run = new JButton("运行");
        JButton clearLog = new JButton("清空");
        JButton outLog = new JButton("日志");
        JTextArea graphFile = new JTextArea("");
        JTextArea walskFile = new JTextArea("walks.txt");
        JTextArea embFile = new JTextArea("karate_czz.emb");
        JTextArea kCategoriesText = new JTextArea("4");
        toolBar.add(selectGraphFileButton);
        toolBar.add(graphFile);
        toolBar.add(loadGraphButton);
        toolBar.add(traverseGraphButton);
        toolBar.add(walskFile);
        toolBar.add(embeddingButton);
        toolBar.add(embFile);
        toolBar.addSeparator();
        toolBar.add(kCategoriesText);
        toolBar.add(clusterButton);
        toolBar.add(dimensionReductionButton);
        toolBar.add(paintButton);
        toolBar.add(run);
        toolBar.add(clearLog);
        toolBar.add(outLog);

        selectGraphFileButton.addActionListener(e -> {
            JFileChooser fc = new JFileChooser();
            fc.addChoosableFileFilter(new FileNameExtensionFilter("邻接表文件(*.edgelist)", "edgelist"));
            int returnVal = fc.showOpenDialog(toolBar);        //0选择1取消
            File file = fc.getSelectedFile();
            if (file != null && returnVal != 1) {                    //选择文件
                graphFile.setText(file.getPath());
            } else {
                //System.out.println(returnVal);
            }
        });

        loadGraphButton.addActionListener(e -> {
            String filePath = graphFile.getText();
            if (StringUtils.isBlank(filePath)) {
                return;
            }
            Log.clear();
            Log.addMessage("图载入内存中");
            Graph = new Graph4N2V<>();
            Graph.loadGraphFromEdgelistFile(filePath, " |,", false, false);
            System.out.print("图文件载入内存完成");
            Log.addMessage("图文件载入内存完成");

        });

        traverseGraphButton.addActionListener(e -> {
            String walksFilePath = walskFile.getText();
            if (Graph == null || StringUtils.isBlank(walksFilePath)) return;
            Log.addMessage("Node2Vec遍历开始");
            n2v = new Node2Vec(Graph, Node2Vec.WalkStorage.ToFile, walksFilePath);
            n2v.setParams(1, 1, 80, 10);
            ArrayList<List<Integer>> walks = n2v.simulate_walks();
            System.out.print("Node2Vec遍历完成");
            Log.addMessage("Node2Vec遍历完成");
            if (CollectionUtils.isEmpty(walks)) {
                System.out.println("，遍历序列存储在文件之中");
            }

        });

        embeddingButton.addActionListener(e -> {
            String walksFilePath = walskFile.getText();
            String embFilePath = embFile.getText();
            if (walksFilePath.length() > 0 && embFilePath.length() > 0) {
                Log.addMessage("Word2Vec嵌入词向量开始");
                w2v = new Word2Vec<String>(Word2Vec.WordType.String, ModelType.Skip_gram, TrainMethod.HS, 5, 128, 5, 0.025f, 5, 3, 1);
                w2v.init(walksFilePath, 1);
                w2v.startTrainning();
                models = w2v.getModels();
                modelsName = new Integer[models.length];
                for (int i = 0; i < models.length; i++) {
                    if (w2v.getWordByIndex(i).equals("")) {
                        modelsName[i] = Integer.parseInt("-" + i);            //没有名字的向量
                        System.out.println("error :" + i);
                    } else {
                        modelsName[i] = Integer.parseInt(w2v.getWordByIndex(i));
                    }
                }
                try {
                    w2v.outputFile(embFilePath);
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
                System.out.println("Word2Vec嵌入词向量完成");
                Log.addMessage("Word2Vec嵌入词向量完成");
            }
        });

        clusterButton.addActionListener(e -> {
            kCategories = Integer.parseInt(kCategoriesText.getText());
            String embFilename = embFile.getText();
            if (kCategories > 1 && models == null && embFilename.length() > 0) {
                loadModelFile(embFilename);                        //从文件装载模型文件
            }
            if (kCategories > 1 && models != null && models.length > 0 && modelsName != null && modelsName.length == models.length) {
                Log.addMessage("k均值聚类分析开始");
                cluster = new KMeans<Integer>();
                for (int i = 0; i < models.length; i++) {
                    cluster.addNode(modelsName[i], models[i]);
                }
                cluster.runCluster(kCategories);
                clusterResult = cluster.getNodes();
                System.out.println("k均值聚类分析完成");
                Log.addMessage("k均值聚类分析完成");
            }
        });

        dimensionReductionButton.addActionListener(e -> {
            if (models != null && models.length > 0) {
                Log.addMessage("降维计算开始");
                int dimension = models[0].getSize();
                Matrix vectors = new Matrix(models.length, dimension);
                for (int i = 0; i < models.length; i++) {
                    for (int j = 0; j < dimension; j++) {
                        vectors.set(i, j, models[i].getVector()[j]);            //行向量
                    }
                }
                pca = Matrix.PCA(vectors);                //主成分分析法
                System.out.println("降维计算完成");
                Log.addMessage("降维计算完成");
            }
        });

        paintButton.addActionListener(e -> {
            if (kCategories > 0 && models != null && models.length > 0 && clusterResult.size() > 0 && pca != null && pca.getRow() == models.length) {
                Point screenLT = new Point(-2, 2);                //（0,0）整个屏幕在第四象限
                cs = new CoordinateSystem(canvas, screenLT, 400, 200);
                //setCoordinateSystem(cs);
                PointSet[] categories = new PointSet[kCategories];
                for (int i = 0; i < kCategories; i++) {
                    categories[i] = new PointSet();
                }
                for (int i = 0; i < models.length; i++) {
                    categories[clusterResult.get(i).label].addPoint(new Point(pca.get(i, 0), pca.get(i, 1)));
                }
                for (int i = 0; i < kCategories; i++) {
                    cs.addPointSet(categories[i]);
                    categories[i].getViewPointSet().set(getRandomColor(), Color.BLACK, 2, 16);        //给每个点集涂色
                    categories[i].getViewPointSet().uniformlySet();
                }
                cs.draw(cs.getCanvas().getGraphics());
                System.out.println(Log.getAll());
            }
        });

        run.addActionListener(e -> {
            String filePath = graphFile.getText();
            if (filePath != null && filePath.length() > 0) {
                Graph = new Graph4N2V<>();
                Graph.loadGraphFromEdgelistFile(filePath, " |,", false, false);
                System.out.print("图文件载入内存完成");
                n2v = new Node2Vec(Graph, Node2Vec.WalkStorage.ToFile, "walks.txt");
                n2v.setParams(1, 1, 80, 10);
                ArrayList<List<Integer>> walks = n2v.simulate_walks();
                System.out.print("Node2Vec遍历完成");
                if (walks == null || (walks != null && walks.size() == 0)) {
                    System.out.println("，遍历序列存储在文件之中");
                }
                //上：图的遍历； 下：通过遍历序列学习节点的向量表示
                w2v = new Word2Vec<String>(Word2Vec.WordType.String, ModelType.Skip_gram, TrainMethod.HS, 5, 128, 5, 0.025f, 5, 3, 1);
                w2v.init("walks.txt", 1);
                w2v.startTrainning();
                models = w2v.getModels();
                try {
                    w2v.outputFile("karate_czz.emb");
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
                System.out.println("Word2Vec嵌入词向量完成");
                //上：word2vec； 下：k均值聚类分析
                cluster = new KMeans<Integer>();
                for (int i = 0; i < models.length; i++) {
                    cluster.addNode(Integer.parseInt(w2v.getWordByIndex(i)), models[i]);
                }
                kCategories = 2;                            //分成3类
                cluster.runCluster(kCategories);
                clusterResult = cluster.getNodes();
                System.out.println("k均值聚类分析完成");
                //上：聚类分析； 下：主成分分析
                Matrix vectors = new Matrix(models.length, w2v.getDimensions());
                for (int i = 0; i < models.length; i++) {
                    for (int j = 0; j < w2v.getDimensions(); j++) {
                        vectors.set(i, j, models[i].getVector()[j]);            //行向量
                    }
                }
                pca = Matrix.PCA(vectors);                //主成分分析
                //上：主成分分析； 下： 可视化
                Point screenLT = new Point(-2, 2);                //（0,0）整个屏幕在第四象限
                cs = new CoordinateSystem(canvas, screenLT, 400, 200);
                //setCoordinateSystem(cs);
                PointSet[] categories = new PointSet[kCategories];
                for (int i = 0; i < kCategories; i++) {
                    categories[i] = new PointSet();
                }
                for (int i = 0; i < models.length; i++) {
                    categories[clusterResult.get(i).label].addPoint(new Point(pca.get(i, 0), pca.get(i, 1)));
                }
                for (int i = 0; i < kCategories; i++) {
                    cs.addPointSet(categories[i]);
                    categories[i].getViewPointSet().set(getRandomColor(), Color.BLACK, 2, 16);        //给每个点集涂色
                    categories[i].getViewPointSet().uniformlySet();
                }
                cs.draw(cs.getCanvas().getGraphics());
            }
        });

        clearLog.addActionListener(e -> Log.clear());

        outLog.addActionListener(e -> System.out.println(Log.getAll()));
    }

    /**
     * 随机一个颜色
     */
    private Color getRandomColor() {
        Random rand = new Random();
        int red, green, blue;
        red = rand.nextInt(256);
        green = rand.nextInt(256);
        blue = rand.nextInt(256);
        return new Color(red, green, blue);
    }

    @SneakyThrows
    private boolean loadModelFile(String modelFileName) {
        boolean ret = false;
        File fp = new File(modelFileName);
        BufferedReader bufread;
        String read;
        String[] splitStr = null;
        int number = 0, dimension = 0;
        int i, j;
        bufread = new BufferedReader(new FileReader(fp));
        if ((read = bufread.readLine()) != null) {
            splitStr = read.split(" |,");
            number = Integer.parseInt(splitStr[0]);                        //向量个数
            dimension = Integer.parseInt(splitStr[1]);                    //向量维度
        }
        if (number > 0 && dimension > 0) {
            this.models = new IVector[number];
            this.modelsName = new Integer[number];
            i = 0;
            for (i = 0; i < number; i++) {
                if ((read = bufread.readLine()) != null) {
                    splitStr = read.split(" |,");
                    if (splitStr.length == dimension + 1) {                    //向量名，向量分量
                        if (splitStr[0].equals("")) {
                            this.modelsName[i] = Integer.parseInt("-" + i);            //没有名字的向量
                            System.out.println("error :" + i);
                        } else {
                            this.modelsName[i] = Integer.parseInt(splitStr[0]);
                        }
                        this.models[i] = new CVector(dimension);
                        for (j = 0; j < dimension; j++) {
                            this.models[i].getVector()[j] = Float.parseFloat(splitStr[j + 1]);
                        }
                    } else break;
                } else break;
            }
            if (i == number) ret = true;                    //加载成功
            else {                                        //加载失败，清空models
                for (j = 0; j < i; j++) {
                    this.models[j] = null;
                }
                this.models = null;
            }
        }
        bufread.close();
        return ret;
    }

    /**
     * 初始化画布
     */
    private void initCanvas() {
        canvas = new JPanel();//画布面板
        canvas.setPreferredSize(new Dimension(970, 800));
        canvas.setBackground(Color.WHITE);
        this.add(canvas, BorderLayout.CENTER);
    }

    /**
     * 返回画布
     */
    public Container getCanvas() {
        return canvas;
    }
}