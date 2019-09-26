import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.WritableImage;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Arc;
import javafx.scene.shape.ArcType;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.text.Font;
import javafx.scene.text.TextFlow;
import javafx.stage.Stage;

public class SinCosTan extends Application {
	private int sceneWidth = 800;
	private int sceneHeight = 400;
	private double circlePointAngle = 0;
	
	Group mainGroup = new Group();
	
	public static void main(String[] args) {
		Application.launch(args);
	}

	public void start(Stage primaryStage) throws Exception {
		Line horizontalLine1 = new Line(0, (sceneHeight / 3) - 1, (sceneWidth / 2) + ((sceneHeight / 2) - (sceneHeight / 3)), (sceneHeight / 3) - 1);
		Line horizontalLine2 = new Line(0, (2 * (sceneHeight / 3)) - 2, (sceneWidth / 2) + ((sceneHeight / 2) - (sceneHeight / 3)), (2 * (sceneHeight / 3)) - 2);
		Line verticalLine1 = new Line((sceneWidth / 2) - ((sceneHeight / 2) - (sceneHeight / 3)), 0, (sceneWidth / 2) - ((sceneHeight / 2) - (sceneHeight / 3)), sceneWidth);
		Line verticalLine2 = new Line((sceneWidth / 2) + ((sceneHeight / 2) - (sceneHeight / 3)), 0, (sceneWidth / 2) + ((sceneHeight / 2) - (sceneHeight / 3)), sceneWidth);
		
		//Center red lines
		for(int i = 11; i < 14; i++) {
			Line horizontalGridLine = new Line((i * (sceneHeight / 12)) + 4, horizontalLine1.getStartY(), (i * (sceneHeight / 12)) + 4, horizontalLine2.getStartY());
			horizontalGridLine.setStroke(Color.rgb(255, 0, 0, 0.33));
			
			mainGroup.getChildren().add(horizontalGridLine);
		}
		
		//Center blue grid lines
		for(int i = 5; i < 8; i++) {
			Line horizontalGridLine = new Line(verticalLine1.getStartX(), i * (sceneHeight / 12), verticalLine2.getStartX(), i * (sceneHeight / 12));
			horizontalGridLine.setStroke(Color.rgb(0, 0, 255, 0.33));
			
			mainGroup.getChildren().add(horizontalGridLine);
		}
		
		Circle mainCircle = new Circle(sceneWidth / 2, (sceneHeight / 2) - 2, sceneHeight / 12, Color.WHITE);
		mainCircle.setStroke(Color.BLACK);
		mainCircle.setFill(Color.TRANSPARENT);
		
		Circle centerPointCircle = new Circle(mainCircle.getCenterX(), mainCircle.getCenterY(), 4);
		
		Arc thetaArc1 = new Arc(mainCircle.getCenterX(), mainCircle.getCenterY(), mainCircle.getRadius() / 2, mainCircle.getRadius() / 2, 0, 0);
		thetaArc1.setType(ArcType.ROUND);
		thetaArc1.setFill(Color.GREY);
		
		Arc thetaArc2 = new Arc(0, thetaArc1.getRadiusY(), thetaArc1.getRadiusX(), thetaArc1.getRadiusY(), 0, 0);
		thetaArc2.setType(ArcType.ROUND);
		thetaArc2.setFill(Color.GREY);
		
		Circle thetaArc2PointCircle = new Circle(thetaArc2.getCenterX(), thetaArc2.getCenterY(), centerPointCircle.getRadius());
		
		Circle thetaArc2OuterCircle = new Circle(thetaArc2.getCenterX(), thetaArc2.getCenterY(), thetaArc2.getRadiusX());
		thetaArc2OuterCircle.setStroke(Color.BLACK);
		thetaArc2OuterCircle.setFill(Color.TRANSPARENT);
		
		Pane thetaArc2Pane = new Pane(thetaArc2, thetaArc2PointCircle, thetaArc2OuterCircle);
		thetaArc2Pane.setMinHeight((thetaArc2.getRadiusY() * 2) + 1);
		thetaArc2Pane.setMinWidth((thetaArc2.getRadiusX() * 2) + 1);
		
		Label thetaLabel = new Label("θ = ");
		thetaLabel.setFont(Font.font("Verdana", 20));
		
		HBox thetaHBox = new HBox(15, thetaLabel, thetaArc2Pane);
		thetaHBox.setLayoutX(380);
		thetaHBox.setLayoutY(2);
		thetaHBox.setAlignment(Pos.CENTER);
		
		Circle pointCircle1 = new Circle(4);
		
		Line pointCircleXLine = new Line();
		pointCircleXLine.setStartX(verticalLine1.getStartX());
		pointCircleXLine.startYProperty().bind(pointCircle1.centerYProperty());
		pointCircleXLine.endXProperty().bind(pointCircle1.centerXProperty());
		pointCircleXLine.endYProperty().bind(pointCircle1.centerYProperty());
		pointCircleXLine.getStrokeDashArray().addAll(5d, 5d);
		pointCircleXLine.setStroke(Color.BLUE);
		
		Line pointCircleYLine = new Line();
		pointCircleYLine.startXProperty().bind(pointCircle1.centerXProperty());
		pointCircleYLine.setStartY(horizontalLine1.getStartY());
		pointCircleYLine.endXProperty().bind(pointCircle1.centerXProperty());
		pointCircleYLine.endYProperty().bind(pointCircle1.centerYProperty());
		pointCircleYLine.getStrokeDashArray().addAll(5d, 5d);
		pointCircleYLine.setStroke(Color.RED);
		
		Circle yPointCircle1 = new Circle(4, Color.RED);
		yPointCircle1.setCenterY(horizontalLine1.getStartY());
		yPointCircle1.centerXProperty().bind(pointCircle1.centerXProperty());
		
		Circle yPointCircle2 = new Circle(4, Color.RED);
		yPointCircle2.setCenterX(verticalLine1.getStartX());
		yPointCircle2.centerYProperty().bind(verticalLine2.startXProperty().subtract(yPointCircle1.centerXProperty()).subtract(2));
		
		Circle xPointCircle = new Circle(4, Color.BLUE);
		xPointCircle.centerYProperty().bind(pointCircle1.centerYProperty());
		xPointCircle.setCenterX(verticalLine1.getStartX());
		
		Arc circleInnerArk = new Arc();
		circleInnerArk.setCenterX(verticalLine1.getStartX());
		circleInnerArk.setCenterY(horizontalLine1.getStartY());
		circleInnerArk.setRadiusX(mainCircle.getCenterX() - verticalLine1.getStartX() - mainCircle.getRadius());
		circleInnerArk.setRadiusY(mainCircle.getCenterX() - verticalLine1.getStartX() - mainCircle.getRadius());
		circleInnerArk.setLength(90);
		circleInnerArk.setFill(Color.WHITE);
		circleInnerArk.setStroke(Color.rgb(255, 0, 0, 0.33));
		
		Arc circleMovingArk = new Arc();
		circleMovingArk.setCenterX(verticalLine1.getStartX());
		circleMovingArk.setCenterY(horizontalLine1.getStartY());
		circleMovingArk.radiusXProperty().bind(pointCircleYLine.startXProperty().subtract(verticalLine1.getStartX()));
		circleMovingArk.radiusYProperty().bind(pointCircleYLine.startXProperty().subtract(verticalLine1.getStartX()));
		circleMovingArk.setLength(90);
		circleMovingArk.setFill(Color.WHITE);
		circleMovingArk.setStroke(Color.RED);
		circleMovingArk.getStrokeDashArray().addAll(5d, 5d);
		
		Arc circleMiddleArk = new Arc();
		circleMiddleArk.setCenterX(verticalLine1.getStartX());
		circleMiddleArk.setCenterY(horizontalLine1.getStartY());
		circleMiddleArk.setRadiusX(mainCircle.getCenterX() - verticalLine1.getStartX());
		circleMiddleArk.setRadiusY(mainCircle.getCenterX() - verticalLine1.getStartX());
		circleMiddleArk.setLength(90);
		circleMiddleArk.setFill(Color.TRANSPARENT);
		circleMiddleArk.setStroke(Color.rgb(255, 0, 0, 0.33));
		
		Arc circleOuterArk = new Arc();
		circleOuterArk.setCenterX(verticalLine1.getStartX());
		circleOuterArk.setCenterY(horizontalLine1.getStartY());
		circleOuterArk.setRadiusX(mainCircle.getCenterX() - verticalLine1.getStartX() + mainCircle.getRadius());
		circleOuterArk.setRadiusY(mainCircle.getCenterX() - verticalLine1.getStartX() + mainCircle.getRadius());
		circleOuterArk.setLength(90);
		circleOuterArk.setFill(Color.TRANSPARENT);
		circleOuterArk.setStroke(Color.rgb(255, 0, 0, 0.33));
		
		Circle pointCircle2 = new Circle(4, Color.WHITE);
		pointCircle2.setStroke(Color.BLACK);
		
		Line circleLine = new Line();
		circleLine.setStroke(Color.PURPLE);
		
		Circle circleLinePointCircle = new Circle(4, Color.PURPLE);
		circleLinePointCircle.setCenterX(verticalLine2.getStartX());
		
		Canvas sinCanvas = new Canvas(verticalLine1.getStartX(), sceneHeight / 3);
		sinCanvas.setLayoutY(sceneHeight / 3);
		
		GraphicsContext sinGraphicsContext = sinCanvas.getGraphicsContext2D();
		sinGraphicsContext.setFill(Color.BLUE);
		
		Canvas cosCanvas = new Canvas(verticalLine1.getStartX(), sceneHeight / 3);
		
		GraphicsContext cosGraphicsContext = cosCanvas.getGraphicsContext2D();
		cosGraphicsContext.setFill(Color.RED);
		
		Canvas tanCanvas = new Canvas(sceneWidth - verticalLine2.getStartX(), sceneHeight);
		tanCanvas.setLayoutX(verticalLine2.getStartX());
		
		GraphicsContext tanGraphicsContext = tanCanvas.getGraphicsContext2D();
		tanGraphicsContext.setLineWidth(2);
		
		AnimationTimer gameLoop = new AnimationTimer() {
			private double y = mainCircle.getCenterY() - 1;
			
			public void handle(long now) {
				pointCircle1.setCenterX(mainCircle.getCenterX() + (mainCircle.getRadius() * Math.cos(Math.toRadians(circlePointAngle))));
				pointCircle1.setCenterY(mainCircle.getCenterY() + (mainCircle.getRadius() * Math.sin(Math.toRadians(circlePointAngle))));
				
				double theta = -1 * Math.toDegrees(Math.atan2(pointCircle1.getCenterY() - mainCircle.getCenterY(), pointCircle1.getCenterX() - mainCircle.getCenterX()));
				
				if(theta > 0) {
					theta = -1 * (360 - theta);
				}
				
				thetaArc1.setLength(theta);
				thetaArc2.setLength(theta);
				
				pointCircle2.setCenterX(mainCircle.getCenterX() - (mainCircle.getRadius() * Math.cos(Math.toRadians(circlePointAngle))));
				pointCircle2.setCenterY(mainCircle.getCenterY() - (mainCircle.getRadius() * Math.sin(Math.toRadians(circlePointAngle))) - 1);
				
				circleLine.setStartX(mainCircle.getCenterX() + (1000 * Math.cos(Math.toRadians(circlePointAngle))));
				circleLine.setStartY(mainCircle.getCenterY() + (1000 * Math.sin(Math.toRadians(circlePointAngle))));
				circleLine.setEndX(mainCircle.getCenterX() - (1000 * Math.cos(Math.toRadians(circlePointAngle))));
				circleLine.setEndY(mainCircle.getCenterY() - (1000 * Math.sin(Math.toRadians(circlePointAngle))));
				
				if(circleLine.getStartX() < verticalLine1.getStartX()) {
					double x1 = verticalLine1.getStartX();
					double y1 = verticalLine1.getStartY();
					double x2 = verticalLine1.getEndX();
					double y2 = verticalLine1.getEndY();
					double x3 = circleLine.getStartX();
					double y3 = circleLine.getStartY();
					double x4 = circleLine.getEndX();
					double y4 = circleLine.getEndY();
					
					double end = ((x1 - x2) * (y3 - y4)) - ((y1 - y2) * (x3 - x4));
					double y = ((((x1 * y2) - (y1 * x2)) * (y3 - y4)) - ((y1 - y2) * ((x3 * y4) - (y3 * x4)))) / end;
					
					circleLine.setStartX(((((x1 * y2) - (y1 * x2)) * (x3 - x4)) - ((x1 - x2) * ((x3 * y4) - (y3 * x4)))) / end);
					
					circleLine.setStartY(y);
					
					circleLinePointCircle.setCenterY(y);
				} else if(circleLine.getEndX() < verticalLine1.getStartX()) {
					double x1 = verticalLine1.getStartX();
					double y1 = verticalLine1.getStartY();
					double x2 = verticalLine1.getEndX();
					double y2 = verticalLine1.getEndY();
					double x3 = circleLine.getStartX();
					double y3 = circleLine.getStartY();
					double x4 = circleLine.getEndX();
					double y4 = circleLine.getEndY();
					
					double end = ((x1 - x2) * (y3 - y4)) - ((y1 - y2) * (x3 - x4));
					double y = ((((x1 * y2) - (y1 * x2)) * (y3 - y4)) - ((y1 - y2) * ((x3 * y4) - (y3 * x4)))) / end;
					
					circleLine.setEndX(((((x1 * y2) - (y1 * x2)) * (x3 - x4)) - ((x1 - x2) * ((x3 * y4) - (y3 * x4)))) / end);
					
					circleLine.setEndY(y);
					
					circleLinePointCircle.setCenterY(y);
				}
				
				if(circleLine.getStartX() > verticalLine2.getStartX()) {
					double x1 = verticalLine2.getStartX();
					double y1 = verticalLine2.getStartY();
					double x2 = verticalLine2.getEndX();
					double y2 = verticalLine2.getEndY();
					double x3 = circleLine.getStartX();
					double y3 = circleLine.getStartY();
					double x4 = circleLine.getEndX();
					double y4 = circleLine.getEndY();
					
					double end = ((x1 - x2) * (y3 - y4)) - ((y1 - y2) * (x3 - x4));
					double y = ((((x1 * y2) - (y1 * x2)) * (y3 - y4)) - ((y1 - y2) * ((x3 * y4) - (y3 * x4)))) / end;
					
					circleLine.setStartX(((((x1 * y2) - (y1 * x2)) * (x3 - x4)) - ((x1 - x2) * ((x3 * y4) - (y3 * x4)))) / end);
					
					circleLine.setStartY(y);
					
					circleLinePointCircle.setCenterY(y);
				} else if(circleLine.getEndX() > verticalLine2.getStartX()) {
					double x1 = verticalLine2.getStartX();
					double y1 = verticalLine2.getStartY();
					double x2 = verticalLine2.getEndX();
					double y2 = verticalLine2.getEndY();
					double x3 = circleLine.getStartX();
					double y3 = circleLine.getStartY();
					double x4 = circleLine.getEndX();
					double y4 = circleLine.getEndY();
					
					double end = ((x1 - x2) * (y3 - y4)) - ((y1 - y2) * (x3 - x4));
					double y = ((((x1 * y2) - (y1 * x2)) * (y3 - y4)) - ((y1 - y2) * ((x3 * y4) - (y3 * x4)))) / end;
					
					circleLine.setEndX(((((x1 * y2) - (y1 * x2)) * (x3 - x4)) - ((x1 - x2) * ((x3 * y4) - (y3 * x4)))) / end);
					
					circleLine.setEndY(y);
					
					circleLinePointCircle.setCenterY(y);
				}
				
				sinGraphicsContext.fillRect(sinCanvas.getWidth() - 1, xPointCircle.getCenterY() - (sceneHeight / 3) - 1, 2, 2);
				
				cosGraphicsContext.fillRect(cosCanvas.getWidth() - 1, yPointCircle2.getCenterY() - 1, 2, 2);
				
				if(Math.abs(circleLinePointCircle.getCenterY() - y) < sceneHeight) {
					//tanGraphicsContext.fillRect(0, circleLinePointCircle.getCenterY(), 2, 2);
					tanGraphicsContext.setStroke(Color.PURPLE);
				} else {
					tanGraphicsContext.setStroke(Color.rgb(128, 0, 128, 0.33));
				}
				
				tanGraphicsContext.strokeLine(1, y, 0, circleLinePointCircle.getCenterY());
				
				if(
					Math.abs(y - (sceneHeight / 2)) < 1
					|| Math.abs(y - (sceneHeight / 3)) < 2
					|| Math.abs(y - (2 * (sceneHeight / 3))) < 2
				) {
					tanGraphicsContext.setStroke(Color.rgb(128, 0, 128, 0.33));
					tanGraphicsContext.strokeLine(1, 0, 1, sceneHeight);
				}
				
				WritableImage cosWritableImage = new WritableImage((int) cosCanvas.getWidth(), (int) cosCanvas.getHeight());
				cosCanvas.snapshot(null, cosWritableImage);
				cosGraphicsContext.clearRect(0, 0, cosCanvas.getWidth(), cosCanvas.getHeight());
				cosGraphicsContext.drawImage(cosWritableImage, -1, 0);
				
				WritableImage sinWritableImage = new WritableImage((int) sinCanvas.getWidth(), (int) sinCanvas.getHeight());
				sinCanvas.snapshot(null, sinWritableImage);
				sinGraphicsContext.clearRect(0, 0, sinCanvas.getWidth(), sinCanvas.getHeight());
				sinGraphicsContext.drawImage(sinWritableImage, -1, 0);
				
				WritableImage wi = new WritableImage((int) tanCanvas.getWidth(), (int) tanCanvas.getHeight());
				tanCanvas.snapshot(null, wi);
				tanGraphicsContext.clearRect(0, 0, tanCanvas.getWidth(), tanCanvas.getHeight());
				tanGraphicsContext.drawImage(wi, 1, 0);
				
				y = circleLinePointCircle.getCenterY() - 1;
				
				circlePointAngle += 1.5;
			}
		};
		
		gameLoop.start();
		
		Group circleGroup = new Group(mainCircle, pointCircleXLine, pointCircleYLine, circleLine, yPointCircle1, yPointCircle2, xPointCircle, circleLinePointCircle, centerPointCircle, pointCircle1, pointCircle2);
		
		Label sinLabel1 = new Label("sin");
		sinLabel1.setTextFill(Color.BLUE);
		sinLabel1.setFont(Font.font("Verdana", 20));
		
		Label sinLabel2 = new Label(" (θ)");
		sinLabel2.setFont(Font.font("Verdana", 20));
		
		TextFlow sinTextFlow = new TextFlow(sinLabel1, sinLabel2);
		sinTextFlow.setLayoutX(5);
		sinTextFlow.setLayoutY(horizontalLine1.getStartY());
		
		Label cosLabel1 = new Label("cos");
		cosLabel1.setTextFill(Color.RED);
		cosLabel1.setFont(Font.font("Verdana", 20));
		
		Label cosLabel2 = new Label(" (θ)");
		cosLabel2.setFont(Font.font("Verdana", 20));
		
		TextFlow cosTextFlow = new TextFlow(cosLabel1, cosLabel2);
		cosTextFlow.setLayoutX(5);
		
		Label tanLabel1 = new Label("- tan");
		tanLabel1.setTextFill(Color.PURPLE);
		tanLabel1.setFont(Font.font("Verdana", 20));
		
		Label tanLabel2 = new Label(" (θ)");
		tanLabel2.setFont(Font.font("Verdana", 20));
		
		TextFlow tanTextFlow = new TextFlow(tanLabel1, tanLabel2);
		tanTextFlow.setLayoutX(sceneWidth - 90);
		tanTextFlow.setStyle("-fx-background-color: white;");
		
		Label watermarkLabel = new Label("Created by Robert D. Rioja");
		watermarkLabel.setFont(Font.font("Verdana", 20));
		watermarkLabel.setLayoutY(sceneHeight - 30);
		watermarkLabel.setLayoutX(5);
		
		mainGroup.getChildren().addAll(circleMovingArk, circleInnerArk, circleMiddleArk, circleOuterArk, sinCanvas, cosCanvas, tanCanvas, thetaArc1, horizontalLine1, horizontalLine2, verticalLine1, verticalLine2, circleGroup, sinTextFlow, cosTextFlow, tanTextFlow, thetaHBox, watermarkLabel);
		
		//Top left red grid lines
		for(int i = 1; i < 4; i++) {
			Line horizontalGridLine = new Line(0, (i * (sceneHeight / 12)) - 1, verticalLine1.getStartX(), (i * (sceneHeight / 12)) - 1);
			horizontalGridLine.setStroke(Color.rgb(255, 0, 0, 0.33));
			
			mainGroup.getChildren().add(horizontalGridLine);
		}
		
		//Middle blue grid lines
		for(int i = 5; i < 8; i++) {
			Line horizontalGridLine = new Line(0, i * (sceneHeight / 12), verticalLine1.getStartX(), i * (sceneHeight / 12));
			horizontalGridLine.setStroke(Color.rgb(0, 0, 255, 0.33));
			
			mainGroup.getChildren().add(horizontalGridLine);
		}
		
		//Right purple lines
		for(int i = 1; i < 12; i++) {
			Line horizontalGridLine = new Line(verticalLine2.getStartX(), i * (sceneHeight / 12), sceneWidth, i * (sceneHeight / 12));
			horizontalGridLine.setStroke(Color.rgb(128, 0, 128, 0.33));
			
			mainGroup.getChildren().add(horizontalGridLine);
		}
		
		Scene scene = new Scene(mainGroup, sceneWidth, sceneHeight);
		
		primaryStage.setScene(scene);
		primaryStage.setTitle("SinCosTan");
		primaryStage.setResizable(false);
		primaryStage.getIcons().add(new Image(getClass().getResource("/icon.png").toString()));
		primaryStage.show();
	}
}
