import java.io.*;
import java.util.*;

public class DepthFirstSearch {

    private static NodeContainer nodeContainer;
    private static Node root,end;
    private static List<Node> state=new ArrayList<>();
    private static List<Node> l=new ArrayList<>();
    private static List<String> steps=new ArrayList<>();




    public static void main(String[] args) {

        init();
        System.out.println(root);

        String outputPath = "data/output.txt";
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter(outputPath));
           dfs(root,end);
            writer.write("Bước thực hiện thuật toán DFS:");
            writer.newLine();
            for (String step : steps) {
                writer.write(step);
                writer.newLine();
            }
            writer.newLine();
            writer.write("Đường đi từ " + root.getName() + " => " + end.getName() + ": " + String.join(" -> ", steps));
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }



    private static void init() {
        nodeContainer=new NodeContainer();


        String inputPath = "data/input.txt";
        int i = 0;
        try (BufferedReader br = new BufferedReader(new FileReader(inputPath))) {
            String line;
            while (!Objects.equals(line = br.readLine(), "")) {
                if(line.contains(" -> ")){
                    String[] names = line.split(" -> ");
                    root=nodeContainer.getNode(names[0]);
                    end=nodeContainer.getNode(names[1]);


                }else {
                    String[] names = line.split(" - ");
                    String parentName = names[0];
                    String childName = names[1];
                    Node parent = null;



                    if(nodeContainer.checkExistNode(parentName)){
                        Node newNode=new Node(parentName);
                        nodeContainer.addNode(newNode);
                        parent=newNode;
                    }else {
                        parent=nodeContainer.getNode(parentName);

                    }

                    if(root==null){
                        root=parent;


                    }

                    if(childName.length()>1){
                        String[] listChildName=childName.split(" ");
                        for (String child:listChildName){
                            addChildToParent(child, parent);
                        }
                    }else {
                        addChildToParent(childName, parent);

                    }


                }


            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void addChildToParent(String childName, Node parent) {
        if(nodeContainer.checkExistNode(childName)){
            Node newNode=new Node(childName);
            nodeContainer.addNode(newNode);
            parent.getChildren().add(newNode);

        }else {
            Node node= nodeContainer.getNode(childName);
            parent.getChildren().add(node);

        }
    }

    private static void dfs(Node start, Node goal){
        if(Objects.equals(goal.getName(), start.getName())){
            steps.add(goal.getName());
            return;
        }
        steps.add(start.getName());
        updateL(start);
        Node current=l.remove(0);
        if(!steps.contains(current.getName())){
            dfs(current,goal);
        }






    }


    private static void updateL(Node start){
        state=new ArrayList<>(start.getChildren());
        if(l.isEmpty()){
            List<Node> reversedList = new ArrayList<>();
            for (int i = state.size() - 1; i >= 0; i--) {
                reversedList.add(state.get(i));
            }
            l.addAll(reversedList);
        }else {
            if(state.isEmpty()){
                System.out.println(start.getName()+" is empty.");
            }
            state.forEach(node -> {
                if(!l.contains(node)&&!steps.contains(node.getName())){
                    l.add(0,node);

                }
            });
        }


    }


}
