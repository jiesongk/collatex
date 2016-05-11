package eu.interedition.collatex.subst;

import java.util.*;
import java.util.function.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

/**
 * Created by ronalddekker on 30/04/16.
 * This is a special version of the edit graph aligner that can handle witnesses with substitutions in them.
 */
public class EditGraphAligner {

    final List<EditGraphTableLabel> labelsWitnessB;
    final List<EditGraphTableLabel> labelsWitnessA;
    final Score[][] cells;

    public static List<EditGraphTableLabel> createLabels(WitnessTree.WitnessNode wit_a) {
        Stream<WitnessTree.WitnessNodeEvent> nodeEventStream = wit_a.depthFirstNodeEventStream();
        // I want to group all the open , text and close events together in a group
        // I first try to do that with a reduce operation
        // but for that to work (left and right) have to be of the same type
        // we might be able to accomplish the same thing with a collect operator
        // Otherwise we have to fall back to the StreamEx extension package.
        // BiPredicate<WitnessTree.WitnessNodeEvent, WitnessTree.WitnessNodeEvent> predicate = (event1, event2) -> event1.getClass().equals(event2.getClass());
        // Two rules:
        // 1. When two text tokens follow each other we should not group them together
        // 1b. A text token followed by anything other than a close tag should not be grouped together
        // 2. When a close tag is followed by an open tag we should not group them together
        // 2b. When a close tag is followed by anything other than a close tag we should not group them together
        BiPredicate<WitnessTree.WitnessNodeEvent, WitnessTree.WitnessNodeEvent> predicate = (event1, event2) -> !(event1.getClass().equals(WitnessTree.WitnessNodeTextEvent.class) && !event2.getClass().equals(WitnessTree.WitnessNodeEndElementEvent.class)) && !(event1.getClass().equals(WitnessTree.WitnessNodeEndElementEvent.class) && !event2.getClass().equals(WitnessTree.WitnessNodeEndElementEvent.class));

        List<List<WitnessTree.WitnessNodeEvent>> lists = nodeEventStream.collect(new GroupOnPredicateCollector<>(predicate));
        Stream<EditGraphTableLabel> editGraphTableLabelStream = lists.stream().map(list -> list.stream().collect(new LabelCollector()));
        return editGraphTableLabelStream.collect(Collectors.toList());
    }




    public EditGraphAligner(WitnessTree.WitnessNode a, WitnessTree.WitnessNode b) {
        labelsWitnessA = createLabels(a);
        labelsWitnessB = createLabels(b);

        cells = new Score[labelsWitnessB.size()+1][labelsWitnessA.size()+1];
        Scorer scorer = new Scorer();

        // init 0,0
        cells[0][0] = new Score(0);

        // fill the first row with gaps
        IntStream.range(1, labelsWitnessA.size()+1).forEach(x -> {
            cells[0][x] = scorer.gap(cells[0][x - 1]);
        });

        // fill the first column with gaps
        IntStream.range(1, labelsWitnessB.size()+1).forEach(y -> {
            cells[y][0] = scorer.gap(cells[y-1][0]);
        });

        // fill the rest of the cells in an y by x fashion
        IntStream.range(1, labelsWitnessB.size()+1).forEach(y -> {
            IntStream.range(1, labelsWitnessA.size()+1).forEach(x -> {
                EditGraphTableLabel tokenB = labelsWitnessB.get(y - 1);
                EditGraphTableLabel tokenA = labelsWitnessA.get(x - 1);
                Score upperLeft = scorer.score(cells[y-1][x-1], tokenB, tokenA);
                Score left = scorer.gap(cells[y][x-1]);
                Score upper = scorer.gap(cells[y-1][x]);
                Score max = Collections.max(Arrays.asList(upperLeft, left, upper), (score, other) -> score.globalScore - other.globalScore);
                cells[y][x] = max;
            });
        });
    }

    public void align() {

    }


    private static class LabelCollector implements java.util.stream.Collector<WitnessTree.WitnessNodeEvent, EditGraphTableLabel, EditGraphTableLabel> {
        @Override
        public Supplier<EditGraphTableLabel> supplier() {
            return EditGraphTableLabel::new;
        }

        @Override
        public BiConsumer<EditGraphTableLabel, WitnessTree.WitnessNodeEvent> accumulator() {
            return (label, event) ->  {
                if (event instanceof WitnessTree.WitnessNodeStartElementEvent) {
                    label.addStartEvent(((WitnessTree.WitnessNodeStartElementEvent)event).node);
                } else if (event instanceof WitnessTree.WitnessNodeEndElementEvent) {
                    label.addEndEvent(((WitnessTree.WitnessNodeEndElementEvent)event).node);
                } else if (event instanceof WitnessTree.WitnessNodeTextEvent) {
                    label.addTextEvent(((WitnessTree.WitnessNodeTextEvent)event).node);
                } else {
                    throw new UnsupportedOperationException();
                }
            };
        }

        @Override
        public BinaryOperator<EditGraphTableLabel> combiner() {
            return (item, item2) -> {
                throw new UnsupportedOperationException();
            };
        }

        @Override
        public Function<EditGraphTableLabel, EditGraphTableLabel> finisher() {
            return (label) -> label;
        }

        @Override
        public Set<Characteristics> characteristics() {
            return Collections.emptySet();
        }
    }

    static class EditGraphTableLabel {
        private List<WitnessTree.WitnessNode> startElements = new ArrayList<>();
        private List<WitnessTree.WitnessNode> endElements = new ArrayList<>();
        private WitnessTree.WitnessNode text;

        public void addStartEvent(WitnessTree.WitnessNode node) {
            startElements.add(node);
        }

        public void addEndEvent(WitnessTree.WitnessNode node) {
            endElements.add(node);
        }

        public void addTextEvent(WitnessTree.WitnessNode node) {
            if (text != null) {
                throw new UnsupportedOperationException();
            }
            this.text = node;
        }

        @Override
        public String toString() {
            String a = startElements.stream().map(WitnessTree.WitnessNode::toString).collect(Collectors.joining(", "));
            String b = text.toString();
            String c = endElements.stream().map(WitnessTree.WitnessNode::toString).collect(Collectors.joining(", "));
            return b+":"+a+":"+c;
        }
    }

    public class Score {
        //TODO: set parent
        int globalScore = 0;

        public Score(int i) {
            this.globalScore = i;
        }

        public Score(Score parent) {
            this.globalScore = parent.globalScore;
        }
    }

    private class Scorer {
        public Score gap(Score parent) {
            return new Score(parent.globalScore -1);
        }

        public Score score(Score parent, EditGraphTableLabel tokenB, EditGraphTableLabel tokenA) {
            if (tokenB.text.data.equals(tokenA.text.data)) {
                return new Score(parent);
            } else {
                return new Score(parent.globalScore - 2);
            }
        }
    }
}
