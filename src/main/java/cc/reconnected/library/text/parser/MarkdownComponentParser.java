package cc.reconnected.library.text.parser;

import cc.reconnected.library.RccLibrary;
import cc.reconnected.library.text.Placeholder;
import eu.pb4.placeholders.api.node.TextNode;
import eu.pb4.placeholders.api.node.parent.ClickActionNode;
import eu.pb4.placeholders.api.node.parent.FormattingNode;
import eu.pb4.placeholders.api.node.parent.HoverNode;
import net.minecraft.text.ClickEvent;
import net.minecraft.util.Formatting;

import java.util.Map;

public class MarkdownComponentParser {
    public static TextNode spoilerFormatting(TextNode[] textNodes) {
        var text = TextNode.asSingle(textNodes);
        return new HoverNode<>(
                TextNode.array(
                        new FormattingNode(TextNode.array(TextNode.of("\u258C".repeat(text.toText().getString().length()))), Formatting.DARK_GRAY)
                ),
                HoverNode.Action.TEXT, text);
    }

    public static TextNode quoteFormatting(TextNode[] textNodes) {
        return new ClickActionNode(
                TextNode.array(
                        new HoverNode<>(
                                TextNode.array(new FormattingNode(textNodes, Formatting.GRAY)),
                                HoverNode.Action.TEXT, TextNode.of("Click to copy"))
                ),
                ClickEvent.Action.COPY_TO_CLIPBOARD, TextNode.asSingle(textNodes)
        );
    }

    public static TextNode urlFormatting(TextNode[] textNodes, TextNode url) {
        var placeholders = Map.of(
                "label", TextNode.wrap(textNodes).toText(),
                "url", url.toText()
        );
        var text = Placeholder.parse(RccLibrary.CONFIG.link, placeholders);
        var hover = Placeholder.parse(RccLibrary.CONFIG.linkHover, placeholders);

        return new HoverNode<>(TextNode.array(
                new ClickActionNode(
                        TextNode.array(
                                TextNode.convert(text)
                        ),
                        ClickEvent.Action.OPEN_URL, url)),
                HoverNode.Action.TEXT, TextNode.convert(hover)
        );
    }
}
