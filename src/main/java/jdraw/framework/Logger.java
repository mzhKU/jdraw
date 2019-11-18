package jdraw.framework;

public class Logger implements FigureListener {
    private Figure f;
    public void setFigure(Figure f) {
        if (f==null) { return; }
        if (this.f.equals(f)) { return; }
        f.removeFigureListener(this);
        this.f = f;
        f.addFigureListener(this);
    }
    @Override
    public void figureChanged(FigureEvent e) {
        if (e.getFigure() != null) {
            System.out.println(e.getFigure().getBounds().getX());
        }
    }
}
