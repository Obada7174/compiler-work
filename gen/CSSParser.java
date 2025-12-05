// Generated from D:/codes-folder/compiler/Test1/grammar/CSSParser.g4 by ANTLR 4.13.2
import org.antlr.v4.runtime.atn.*;
import org.antlr.v4.runtime.dfa.DFA;
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.misc.*;
import org.antlr.v4.runtime.tree.*;
import java.util.List;
import java.util.Iterator;
import java.util.ArrayList;

@SuppressWarnings({"all", "warnings", "unchecked", "unused", "cast", "CheckReturnValue", "this-escape"})
public class CSSParser extends Parser {
	static { RuntimeMetaData.checkVersion("4.13.2", RuntimeMetaData.VERSION); }

	protected static final DFA[] _decisionToDFA;
	protected static final PredictionContextCache _sharedContextCache =
		new PredictionContextCache();
	public static final int
		HASH=1, DOT=2, STAR=3, PSEUDO_CLASS=4, PSEUDO_ELEMENT=5, GT=6, PLUS=7, 
		TILDE=8, LBRACE=9, RBRACE=10, LPAREN=11, RPAREN=12, LBRACKET=13, RBRACKET=14, 
		COLON=15, SEMICOLON=16, COMMA=17, EQUALS=18, NUMBER=19, DIMENSION=20, 
		COLOR_HEX=21, STRING=22, CUSTOM_PROPERTY=23, KEYWORD=24, IDENTIFIER=25, 
		FUNCTION_NAME=26, IMPORTANT=27, URL=28, AT_KEYWORD=29, WS=30, COMMENT=31;
	public static final int
		RULE_stylesheet = 0, RULE_atRule = 1, RULE_ruleSet = 2, RULE_selectorList = 3, 
		RULE_selector = 4, RULE_simpleSelector = 5, RULE_selectorPart = 6, RULE_combinator = 7, 
		RULE_declaration = 8, RULE_value = 9, RULE_valueItem = 10, RULE_functionCall = 11;
	private static String[] makeRuleNames() {
		return new String[] {
			"stylesheet", "atRule", "ruleSet", "selectorList", "selector", "simpleSelector", 
			"selectorPart", "combinator", "declaration", "value", "valueItem", "functionCall"
		};
	}
	public static final String[] ruleNames = makeRuleNames();

	private static String[] makeLiteralNames() {
		return new String[] {
			null, "'#'", "'.'", "'*'", null, null, "'>'", "'+'", "'~'", "'{'", "'}'", 
			"'('", "')'", "'['", "']'", "':'", "';'", "','", "'='", null, null, null, 
			null, null, null, null, null, "'!important'"
		};
	}
	private static final String[] _LITERAL_NAMES = makeLiteralNames();
	private static String[] makeSymbolicNames() {
		return new String[] {
			null, "HASH", "DOT", "STAR", "PSEUDO_CLASS", "PSEUDO_ELEMENT", "GT", 
			"PLUS", "TILDE", "LBRACE", "RBRACE", "LPAREN", "RPAREN", "LBRACKET", 
			"RBRACKET", "COLON", "SEMICOLON", "COMMA", "EQUALS", "NUMBER", "DIMENSION", 
			"COLOR_HEX", "STRING", "CUSTOM_PROPERTY", "KEYWORD", "IDENTIFIER", "FUNCTION_NAME", 
			"IMPORTANT", "URL", "AT_KEYWORD", "WS", "COMMENT"
		};
	}
	private static final String[] _SYMBOLIC_NAMES = makeSymbolicNames();
	public static final Vocabulary VOCABULARY = new VocabularyImpl(_LITERAL_NAMES, _SYMBOLIC_NAMES);

	/**
	 * @deprecated Use {@link #VOCABULARY} instead.
	 */
	@Deprecated
	public static final String[] tokenNames;
	static {
		tokenNames = new String[_SYMBOLIC_NAMES.length];
		for (int i = 0; i < tokenNames.length; i++) {
			tokenNames[i] = VOCABULARY.getLiteralName(i);
			if (tokenNames[i] == null) {
				tokenNames[i] = VOCABULARY.getSymbolicName(i);
			}

			if (tokenNames[i] == null) {
				tokenNames[i] = "<INVALID>";
			}
		}
	}

	@Override
	@Deprecated
	public String[] getTokenNames() {
		return tokenNames;
	}

	@Override

	public Vocabulary getVocabulary() {
		return VOCABULARY;
	}

	@Override
	public String getGrammarFileName() { return "CSSParser.g4"; }

	@Override
	public String[] getRuleNames() { return ruleNames; }

	@Override
	public String getSerializedATN() { return _serializedATN; }

	@Override
	public ATN getATN() { return _ATN; }

	public CSSParser(TokenStream input) {
		super(input);
		_interp = new ParserATNSimulator(this,_ATN,_decisionToDFA,_sharedContextCache);
	}

	@SuppressWarnings("CheckReturnValue")
	public static class StylesheetContext extends ParserRuleContext {
		public TerminalNode EOF() { return getToken(CSSParser.EOF, 0); }
		public List<RuleSetContext> ruleSet() {
			return getRuleContexts(RuleSetContext.class);
		}
		public RuleSetContext ruleSet(int i) {
			return getRuleContext(RuleSetContext.class,i);
		}
		public List<AtRuleContext> atRule() {
			return getRuleContexts(AtRuleContext.class);
		}
		public AtRuleContext atRule(int i) {
			return getRuleContext(AtRuleContext.class,i);
		}
		public StylesheetContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_stylesheet; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof CSSParserListener ) ((CSSParserListener)listener).enterStylesheet(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof CSSParserListener ) ((CSSParserListener)listener).exitStylesheet(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof CSSParserVisitor ) return ((CSSParserVisitor<? extends T>)visitor).visitStylesheet(this);
			else return visitor.visitChildren(this);
		}
	}

	public final StylesheetContext stylesheet() throws RecognitionException {
		StylesheetContext _localctx = new StylesheetContext(_ctx, getState());
		enterRule(_localctx, 0, RULE_stylesheet);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(28);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while ((((_la) & ~0x3f) == 0 && ((1L << _la) & 570433598L) != 0)) {
				{
				setState(26);
				_errHandler.sync(this);
				switch (_input.LA(1)) {
				case HASH:
				case DOT:
				case STAR:
				case PSEUDO_CLASS:
				case PSEUDO_ELEMENT:
				case LBRACKET:
				case IDENTIFIER:
					{
					setState(24);
					ruleSet();
					}
					break;
				case AT_KEYWORD:
					{
					setState(25);
					atRule();
					}
					break;
				default:
					throw new NoViableAltException(this);
				}
				}
				setState(30);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(31);
			match(EOF);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class AtRuleContext extends ParserRuleContext {
		public TerminalNode AT_KEYWORD() { return getToken(CSSParser.AT_KEYWORD, 0); }
		public TerminalNode LBRACE() { return getToken(CSSParser.LBRACE, 0); }
		public TerminalNode RBRACE() { return getToken(CSSParser.RBRACE, 0); }
		public ValueContext value() {
			return getRuleContext(ValueContext.class,0);
		}
		public List<RuleSetContext> ruleSet() {
			return getRuleContexts(RuleSetContext.class);
		}
		public RuleSetContext ruleSet(int i) {
			return getRuleContext(RuleSetContext.class,i);
		}
		public TerminalNode SEMICOLON() { return getToken(CSSParser.SEMICOLON, 0); }
		public AtRuleContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_atRule; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof CSSParserListener ) ((CSSParserListener)listener).enterAtRule(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof CSSParserListener ) ((CSSParserListener)listener).exitAtRule(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof CSSParserVisitor ) return ((CSSParserVisitor<? extends T>)visitor).visitAtRule(this);
			else return visitor.visitChildren(this);
		}
	}

	public final AtRuleContext atRule() throws RecognitionException {
		AtRuleContext _localctx = new AtRuleContext(_ctx, getState());
		enterRule(_localctx, 2, RULE_atRule);
		int _la;
		try {
			setState(50);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,5,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(33);
				match(AT_KEYWORD);
				setState(35);
				_errHandler.sync(this);
				_la = _input.LA(1);
				if ((((_la) & ~0x3f) == 0 && ((1L << _la) & 309854208L) != 0)) {
					{
					setState(34);
					value();
					}
				}

				setState(37);
				match(LBRACE);
				setState(41);
				_errHandler.sync(this);
				_la = _input.LA(1);
				while ((((_la) & ~0x3f) == 0 && ((1L << _la) & 33562686L) != 0)) {
					{
					{
					setState(38);
					ruleSet();
					}
					}
					setState(43);
					_errHandler.sync(this);
					_la = _input.LA(1);
				}
				setState(44);
				match(RBRACE);
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(45);
				match(AT_KEYWORD);
				setState(47);
				_errHandler.sync(this);
				_la = _input.LA(1);
				if ((((_la) & ~0x3f) == 0 && ((1L << _la) & 309854208L) != 0)) {
					{
					setState(46);
					value();
					}
				}

				setState(49);
				match(SEMICOLON);
				}
				break;
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class RuleSetContext extends ParserRuleContext {
		public SelectorListContext selectorList() {
			return getRuleContext(SelectorListContext.class,0);
		}
		public TerminalNode LBRACE() { return getToken(CSSParser.LBRACE, 0); }
		public TerminalNode RBRACE() { return getToken(CSSParser.RBRACE, 0); }
		public List<DeclarationContext> declaration() {
			return getRuleContexts(DeclarationContext.class);
		}
		public DeclarationContext declaration(int i) {
			return getRuleContext(DeclarationContext.class,i);
		}
		public RuleSetContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_ruleSet; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof CSSParserListener ) ((CSSParserListener)listener).enterRuleSet(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof CSSParserListener ) ((CSSParserListener)listener).exitRuleSet(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof CSSParserVisitor ) return ((CSSParserVisitor<? extends T>)visitor).visitRuleSet(this);
			else return visitor.visitChildren(this);
		}
	}

	public final RuleSetContext ruleSet() throws RecognitionException {
		RuleSetContext _localctx = new RuleSetContext(_ctx, getState());
		enterRule(_localctx, 4, RULE_ruleSet);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(52);
			selectorList();
			setState(53);
			match(LBRACE);
			setState(57);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==IDENTIFIER) {
				{
				{
				setState(54);
				declaration();
				}
				}
				setState(59);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(60);
			match(RBRACE);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class SelectorListContext extends ParserRuleContext {
		public List<SelectorContext> selector() {
			return getRuleContexts(SelectorContext.class);
		}
		public SelectorContext selector(int i) {
			return getRuleContext(SelectorContext.class,i);
		}
		public List<TerminalNode> COMMA() { return getTokens(CSSParser.COMMA); }
		public TerminalNode COMMA(int i) {
			return getToken(CSSParser.COMMA, i);
		}
		public SelectorListContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_selectorList; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof CSSParserListener ) ((CSSParserListener)listener).enterSelectorList(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof CSSParserListener ) ((CSSParserListener)listener).exitSelectorList(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof CSSParserVisitor ) return ((CSSParserVisitor<? extends T>)visitor).visitSelectorList(this);
			else return visitor.visitChildren(this);
		}
	}

	public final SelectorListContext selectorList() throws RecognitionException {
		SelectorListContext _localctx = new SelectorListContext(_ctx, getState());
		enterRule(_localctx, 6, RULE_selectorList);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(62);
			selector();
			setState(67);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==COMMA) {
				{
				{
				setState(63);
				match(COMMA);
				setState(64);
				selector();
				}
				}
				setState(69);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class SelectorContext extends ParserRuleContext {
		public List<SimpleSelectorContext> simpleSelector() {
			return getRuleContexts(SimpleSelectorContext.class);
		}
		public SimpleSelectorContext simpleSelector(int i) {
			return getRuleContext(SimpleSelectorContext.class,i);
		}
		public List<CombinatorContext> combinator() {
			return getRuleContexts(CombinatorContext.class);
		}
		public CombinatorContext combinator(int i) {
			return getRuleContext(CombinatorContext.class,i);
		}
		public SelectorContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_selector; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof CSSParserListener ) ((CSSParserListener)listener).enterSelector(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof CSSParserListener ) ((CSSParserListener)listener).exitSelector(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof CSSParserVisitor ) return ((CSSParserVisitor<? extends T>)visitor).visitSelector(this);
			else return visitor.visitChildren(this);
		}
	}

	public final SelectorContext selector() throws RecognitionException {
		SelectorContext _localctx = new SelectorContext(_ctx, getState());
		enterRule(_localctx, 8, RULE_selector);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(70);
			simpleSelector();
			setState(76);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while ((((_la) & ~0x3f) == 0 && ((1L << _la) & 448L) != 0)) {
				{
				{
				setState(71);
				combinator();
				setState(72);
				simpleSelector();
				}
				}
				setState(78);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class SimpleSelectorContext extends ParserRuleContext {
		public List<SelectorPartContext> selectorPart() {
			return getRuleContexts(SelectorPartContext.class);
		}
		public SelectorPartContext selectorPart(int i) {
			return getRuleContext(SelectorPartContext.class,i);
		}
		public SimpleSelectorContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_simpleSelector; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof CSSParserListener ) ((CSSParserListener)listener).enterSimpleSelector(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof CSSParserListener ) ((CSSParserListener)listener).exitSimpleSelector(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof CSSParserVisitor ) return ((CSSParserVisitor<? extends T>)visitor).visitSimpleSelector(this);
			else return visitor.visitChildren(this);
		}
	}

	public final SimpleSelectorContext simpleSelector() throws RecognitionException {
		SimpleSelectorContext _localctx = new SimpleSelectorContext(_ctx, getState());
		enterRule(_localctx, 10, RULE_simpleSelector);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(80); 
			_errHandler.sync(this);
			_la = _input.LA(1);
			do {
				{
				{
				setState(79);
				selectorPart();
				}
				}
				setState(82); 
				_errHandler.sync(this);
				_la = _input.LA(1);
			} while ( (((_la) & ~0x3f) == 0 && ((1L << _la) & 33562686L) != 0) );
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class SelectorPartContext extends ParserRuleContext {
		public SelectorPartContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_selectorPart; }
	 
		public SelectorPartContext() { }
		public void copyFrom(SelectorPartContext ctx) {
			super.copyFrom(ctx);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class UniversalSelectorContext extends SelectorPartContext {
		public TerminalNode STAR() { return getToken(CSSParser.STAR, 0); }
		public UniversalSelectorContext(SelectorPartContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof CSSParserListener ) ((CSSParserListener)listener).enterUniversalSelector(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof CSSParserListener ) ((CSSParserListener)listener).exitUniversalSelector(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof CSSParserVisitor ) return ((CSSParserVisitor<? extends T>)visitor).visitUniversalSelector(this);
			else return visitor.visitChildren(this);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class IdSelectorContext extends SelectorPartContext {
		public TerminalNode HASH() { return getToken(CSSParser.HASH, 0); }
		public TerminalNode IDENTIFIER() { return getToken(CSSParser.IDENTIFIER, 0); }
		public IdSelectorContext(SelectorPartContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof CSSParserListener ) ((CSSParserListener)listener).enterIdSelector(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof CSSParserListener ) ((CSSParserListener)listener).exitIdSelector(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof CSSParserVisitor ) return ((CSSParserVisitor<? extends T>)visitor).visitIdSelector(this);
			else return visitor.visitChildren(this);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class ClassSelectorContext extends SelectorPartContext {
		public TerminalNode DOT() { return getToken(CSSParser.DOT, 0); }
		public TerminalNode IDENTIFIER() { return getToken(CSSParser.IDENTIFIER, 0); }
		public ClassSelectorContext(SelectorPartContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof CSSParserListener ) ((CSSParserListener)listener).enterClassSelector(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof CSSParserListener ) ((CSSParserListener)listener).exitClassSelector(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof CSSParserVisitor ) return ((CSSParserVisitor<? extends T>)visitor).visitClassSelector(this);
			else return visitor.visitChildren(this);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class PseudoElementSelectorContext extends SelectorPartContext {
		public TerminalNode PSEUDO_ELEMENT() { return getToken(CSSParser.PSEUDO_ELEMENT, 0); }
		public PseudoElementSelectorContext(SelectorPartContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof CSSParserListener ) ((CSSParserListener)listener).enterPseudoElementSelector(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof CSSParserListener ) ((CSSParserListener)listener).exitPseudoElementSelector(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof CSSParserVisitor ) return ((CSSParserVisitor<? extends T>)visitor).visitPseudoElementSelector(this);
			else return visitor.visitChildren(this);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class ElementSelectorContext extends SelectorPartContext {
		public TerminalNode IDENTIFIER() { return getToken(CSSParser.IDENTIFIER, 0); }
		public ElementSelectorContext(SelectorPartContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof CSSParserListener ) ((CSSParserListener)listener).enterElementSelector(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof CSSParserListener ) ((CSSParserListener)listener).exitElementSelector(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof CSSParserVisitor ) return ((CSSParserVisitor<? extends T>)visitor).visitElementSelector(this);
			else return visitor.visitChildren(this);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class AttributeSelectorContext extends SelectorPartContext {
		public TerminalNode LBRACKET() { return getToken(CSSParser.LBRACKET, 0); }
		public TerminalNode IDENTIFIER() { return getToken(CSSParser.IDENTIFIER, 0); }
		public TerminalNode RBRACKET() { return getToken(CSSParser.RBRACKET, 0); }
		public TerminalNode EQUALS() { return getToken(CSSParser.EQUALS, 0); }
		public ValueContext value() {
			return getRuleContext(ValueContext.class,0);
		}
		public AttributeSelectorContext(SelectorPartContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof CSSParserListener ) ((CSSParserListener)listener).enterAttributeSelector(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof CSSParserListener ) ((CSSParserListener)listener).exitAttributeSelector(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof CSSParserVisitor ) return ((CSSParserVisitor<? extends T>)visitor).visitAttributeSelector(this);
			else return visitor.visitChildren(this);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class PseudoClassSelectorContext extends SelectorPartContext {
		public TerminalNode PSEUDO_CLASS() { return getToken(CSSParser.PSEUDO_CLASS, 0); }
		public PseudoClassSelectorContext(SelectorPartContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof CSSParserListener ) ((CSSParserListener)listener).enterPseudoClassSelector(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof CSSParserListener ) ((CSSParserListener)listener).exitPseudoClassSelector(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof CSSParserVisitor ) return ((CSSParserVisitor<? extends T>)visitor).visitPseudoClassSelector(this);
			else return visitor.visitChildren(this);
		}
	}

	public final SelectorPartContext selectorPart() throws RecognitionException {
		SelectorPartContext _localctx = new SelectorPartContext(_ctx, getState());
		enterRule(_localctx, 12, RULE_selectorPart);
		int _la;
		try {
			setState(99);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case IDENTIFIER:
				_localctx = new ElementSelectorContext(_localctx);
				enterOuterAlt(_localctx, 1);
				{
				setState(84);
				match(IDENTIFIER);
				}
				break;
			case DOT:
				_localctx = new ClassSelectorContext(_localctx);
				enterOuterAlt(_localctx, 2);
				{
				setState(85);
				match(DOT);
				setState(86);
				match(IDENTIFIER);
				}
				break;
			case HASH:
				_localctx = new IdSelectorContext(_localctx);
				enterOuterAlt(_localctx, 3);
				{
				setState(87);
				match(HASH);
				setState(88);
				match(IDENTIFIER);
				}
				break;
			case STAR:
				_localctx = new UniversalSelectorContext(_localctx);
				enterOuterAlt(_localctx, 4);
				{
				setState(89);
				match(STAR);
				}
				break;
			case PSEUDO_CLASS:
				_localctx = new PseudoClassSelectorContext(_localctx);
				enterOuterAlt(_localctx, 5);
				{
				setState(90);
				match(PSEUDO_CLASS);
				}
				break;
			case PSEUDO_ELEMENT:
				_localctx = new PseudoElementSelectorContext(_localctx);
				enterOuterAlt(_localctx, 6);
				{
				setState(91);
				match(PSEUDO_ELEMENT);
				}
				break;
			case LBRACKET:
				_localctx = new AttributeSelectorContext(_localctx);
				enterOuterAlt(_localctx, 7);
				{
				setState(92);
				match(LBRACKET);
				setState(93);
				match(IDENTIFIER);
				setState(96);
				_errHandler.sync(this);
				_la = _input.LA(1);
				if (_la==EQUALS) {
					{
					setState(94);
					match(EQUALS);
					setState(95);
					value();
					}
				}

				setState(98);
				match(RBRACKET);
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class CombinatorContext extends ParserRuleContext {
		public TerminalNode GT() { return getToken(CSSParser.GT, 0); }
		public TerminalNode PLUS() { return getToken(CSSParser.PLUS, 0); }
		public TerminalNode TILDE() { return getToken(CSSParser.TILDE, 0); }
		public CombinatorContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_combinator; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof CSSParserListener ) ((CSSParserListener)listener).enterCombinator(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof CSSParserListener ) ((CSSParserListener)listener).exitCombinator(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof CSSParserVisitor ) return ((CSSParserVisitor<? extends T>)visitor).visitCombinator(this);
			else return visitor.visitChildren(this);
		}
	}

	public final CombinatorContext combinator() throws RecognitionException {
		CombinatorContext _localctx = new CombinatorContext(_ctx, getState());
		enterRule(_localctx, 14, RULE_combinator);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(101);
			_la = _input.LA(1);
			if ( !((((_la) & ~0x3f) == 0 && ((1L << _la) & 448L) != 0)) ) {
			_errHandler.recoverInline(this);
			}
			else {
				if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
				_errHandler.reportMatch(this);
				consume();
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class DeclarationContext extends ParserRuleContext {
		public TerminalNode IDENTIFIER() { return getToken(CSSParser.IDENTIFIER, 0); }
		public TerminalNode COLON() { return getToken(CSSParser.COLON, 0); }
		public ValueContext value() {
			return getRuleContext(ValueContext.class,0);
		}
		public TerminalNode IMPORTANT() { return getToken(CSSParser.IMPORTANT, 0); }
		public TerminalNode SEMICOLON() { return getToken(CSSParser.SEMICOLON, 0); }
		public DeclarationContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_declaration; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof CSSParserListener ) ((CSSParserListener)listener).enterDeclaration(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof CSSParserListener ) ((CSSParserListener)listener).exitDeclaration(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof CSSParserVisitor ) return ((CSSParserVisitor<? extends T>)visitor).visitDeclaration(this);
			else return visitor.visitChildren(this);
		}
	}

	public final DeclarationContext declaration() throws RecognitionException {
		DeclarationContext _localctx = new DeclarationContext(_ctx, getState());
		enterRule(_localctx, 16, RULE_declaration);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(103);
			match(IDENTIFIER);
			setState(104);
			match(COLON);
			setState(105);
			value();
			setState(107);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==IMPORTANT) {
				{
				setState(106);
				match(IMPORTANT);
				}
			}

			setState(110);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==SEMICOLON) {
				{
				setState(109);
				match(SEMICOLON);
				}
			}

			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class ValueContext extends ParserRuleContext {
		public List<ValueItemContext> valueItem() {
			return getRuleContexts(ValueItemContext.class);
		}
		public ValueItemContext valueItem(int i) {
			return getRuleContext(ValueItemContext.class,i);
		}
		public List<TerminalNode> COMMA() { return getTokens(CSSParser.COMMA); }
		public TerminalNode COMMA(int i) {
			return getToken(CSSParser.COMMA, i);
		}
		public ValueContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_value; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof CSSParserListener ) ((CSSParserListener)listener).enterValue(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof CSSParserListener ) ((CSSParserListener)listener).exitValue(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof CSSParserVisitor ) return ((CSSParserVisitor<? extends T>)visitor).visitValue(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ValueContext value() throws RecognitionException {
		ValueContext _localctx = new ValueContext(_ctx, getState());
		enterRule(_localctx, 18, RULE_value);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(112);
			valueItem();
			setState(117);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==COMMA) {
				{
				{
				setState(113);
				match(COMMA);
				setState(114);
				valueItem();
				}
				}
				setState(119);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class ValueItemContext extends ParserRuleContext {
		public ValueItemContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_valueItem; }
	 
		public ValueItemContext() { }
		public void copyFrom(ValueItemContext ctx) {
			super.copyFrom(ctx);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class DimensionValueContext extends ValueItemContext {
		public TerminalNode DIMENSION() { return getToken(CSSParser.DIMENSION, 0); }
		public DimensionValueContext(ValueItemContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof CSSParserListener ) ((CSSParserListener)listener).enterDimensionValue(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof CSSParserListener ) ((CSSParserListener)listener).exitDimensionValue(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof CSSParserVisitor ) return ((CSSParserVisitor<? extends T>)visitor).visitDimensionValue(this);
			else return visitor.visitChildren(this);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class NumberValueContext extends ValueItemContext {
		public TerminalNode NUMBER() { return getToken(CSSParser.NUMBER, 0); }
		public NumberValueContext(ValueItemContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof CSSParserListener ) ((CSSParserListener)listener).enterNumberValue(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof CSSParserListener ) ((CSSParserListener)listener).exitNumberValue(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof CSSParserVisitor ) return ((CSSParserVisitor<? extends T>)visitor).visitNumberValue(this);
			else return visitor.visitChildren(this);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class ColorValueContext extends ValueItemContext {
		public TerminalNode COLOR_HEX() { return getToken(CSSParser.COLOR_HEX, 0); }
		public ColorValueContext(ValueItemContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof CSSParserListener ) ((CSSParserListener)listener).enterColorValue(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof CSSParserListener ) ((CSSParserListener)listener).exitColorValue(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof CSSParserVisitor ) return ((CSSParserVisitor<? extends T>)visitor).visitColorValue(this);
			else return visitor.visitChildren(this);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class FunctionValueContext extends ValueItemContext {
		public FunctionCallContext functionCall() {
			return getRuleContext(FunctionCallContext.class,0);
		}
		public FunctionValueContext(ValueItemContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof CSSParserListener ) ((CSSParserListener)listener).enterFunctionValue(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof CSSParserListener ) ((CSSParserListener)listener).exitFunctionValue(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof CSSParserVisitor ) return ((CSSParserVisitor<? extends T>)visitor).visitFunctionValue(this);
			else return visitor.visitChildren(this);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class IdentifierValueContext extends ValueItemContext {
		public TerminalNode IDENTIFIER() { return getToken(CSSParser.IDENTIFIER, 0); }
		public IdentifierValueContext(ValueItemContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof CSSParserListener ) ((CSSParserListener)listener).enterIdentifierValue(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof CSSParserListener ) ((CSSParserListener)listener).exitIdentifierValue(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof CSSParserVisitor ) return ((CSSParserVisitor<? extends T>)visitor).visitIdentifierValue(this);
			else return visitor.visitChildren(this);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class StringValueContext extends ValueItemContext {
		public TerminalNode STRING() { return getToken(CSSParser.STRING, 0); }
		public StringValueContext(ValueItemContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof CSSParserListener ) ((CSSParserListener)listener).enterStringValue(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof CSSParserListener ) ((CSSParserListener)listener).exitStringValue(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof CSSParserVisitor ) return ((CSSParserVisitor<? extends T>)visitor).visitStringValue(this);
			else return visitor.visitChildren(this);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class UrlValueContext extends ValueItemContext {
		public TerminalNode URL() { return getToken(CSSParser.URL, 0); }
		public UrlValueContext(ValueItemContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof CSSParserListener ) ((CSSParserListener)listener).enterUrlValue(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof CSSParserListener ) ((CSSParserListener)listener).exitUrlValue(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof CSSParserVisitor ) return ((CSSParserVisitor<? extends T>)visitor).visitUrlValue(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ValueItemContext valueItem() throws RecognitionException {
		ValueItemContext _localctx = new ValueItemContext(_ctx, getState());
		enterRule(_localctx, 20, RULE_valueItem);
		try {
			setState(127);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,15,_ctx) ) {
			case 1:
				_localctx = new DimensionValueContext(_localctx);
				enterOuterAlt(_localctx, 1);
				{
				setState(120);
				match(DIMENSION);
				}
				break;
			case 2:
				_localctx = new NumberValueContext(_localctx);
				enterOuterAlt(_localctx, 2);
				{
				setState(121);
				match(NUMBER);
				}
				break;
			case 3:
				_localctx = new ColorValueContext(_localctx);
				enterOuterAlt(_localctx, 3);
				{
				setState(122);
				match(COLOR_HEX);
				}
				break;
			case 4:
				_localctx = new StringValueContext(_localctx);
				enterOuterAlt(_localctx, 4);
				{
				setState(123);
				match(STRING);
				}
				break;
			case 5:
				_localctx = new IdentifierValueContext(_localctx);
				enterOuterAlt(_localctx, 5);
				{
				setState(124);
				match(IDENTIFIER);
				}
				break;
			case 6:
				_localctx = new FunctionValueContext(_localctx);
				enterOuterAlt(_localctx, 6);
				{
				setState(125);
				functionCall();
				}
				break;
			case 7:
				_localctx = new UrlValueContext(_localctx);
				enterOuterAlt(_localctx, 7);
				{
				setState(126);
				match(URL);
				}
				break;
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class FunctionCallContext extends ParserRuleContext {
		public TerminalNode IDENTIFIER() { return getToken(CSSParser.IDENTIFIER, 0); }
		public TerminalNode LPAREN() { return getToken(CSSParser.LPAREN, 0); }
		public TerminalNode RPAREN() { return getToken(CSSParser.RPAREN, 0); }
		public ValueContext value() {
			return getRuleContext(ValueContext.class,0);
		}
		public FunctionCallContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_functionCall; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof CSSParserListener ) ((CSSParserListener)listener).enterFunctionCall(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof CSSParserListener ) ((CSSParserListener)listener).exitFunctionCall(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof CSSParserVisitor ) return ((CSSParserVisitor<? extends T>)visitor).visitFunctionCall(this);
			else return visitor.visitChildren(this);
		}
	}

	public final FunctionCallContext functionCall() throws RecognitionException {
		FunctionCallContext _localctx = new FunctionCallContext(_ctx, getState());
		enterRule(_localctx, 22, RULE_functionCall);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(129);
			match(IDENTIFIER);
			setState(130);
			match(LPAREN);
			setState(132);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if ((((_la) & ~0x3f) == 0 && ((1L << _la) & 309854208L) != 0)) {
				{
				setState(131);
				value();
				}
			}

			setState(134);
			match(RPAREN);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static final String _serializedATN =
		"\u0004\u0001\u001f\u0089\u0002\u0000\u0007\u0000\u0002\u0001\u0007\u0001"+
		"\u0002\u0002\u0007\u0002\u0002\u0003\u0007\u0003\u0002\u0004\u0007\u0004"+
		"\u0002\u0005\u0007\u0005\u0002\u0006\u0007\u0006\u0002\u0007\u0007\u0007"+
		"\u0002\b\u0007\b\u0002\t\u0007\t\u0002\n\u0007\n\u0002\u000b\u0007\u000b"+
		"\u0001\u0000\u0001\u0000\u0005\u0000\u001b\b\u0000\n\u0000\f\u0000\u001e"+
		"\t\u0000\u0001\u0000\u0001\u0000\u0001\u0001\u0001\u0001\u0003\u0001$"+
		"\b\u0001\u0001\u0001\u0001\u0001\u0005\u0001(\b\u0001\n\u0001\f\u0001"+
		"+\t\u0001\u0001\u0001\u0001\u0001\u0001\u0001\u0003\u00010\b\u0001\u0001"+
		"\u0001\u0003\u00013\b\u0001\u0001\u0002\u0001\u0002\u0001\u0002\u0005"+
		"\u00028\b\u0002\n\u0002\f\u0002;\t\u0002\u0001\u0002\u0001\u0002\u0001"+
		"\u0003\u0001\u0003\u0001\u0003\u0005\u0003B\b\u0003\n\u0003\f\u0003E\t"+
		"\u0003\u0001\u0004\u0001\u0004\u0001\u0004\u0001\u0004\u0005\u0004K\b"+
		"\u0004\n\u0004\f\u0004N\t\u0004\u0001\u0005\u0004\u0005Q\b\u0005\u000b"+
		"\u0005\f\u0005R\u0001\u0006\u0001\u0006\u0001\u0006\u0001\u0006\u0001"+
		"\u0006\u0001\u0006\u0001\u0006\u0001\u0006\u0001\u0006\u0001\u0006\u0001"+
		"\u0006\u0001\u0006\u0003\u0006a\b\u0006\u0001\u0006\u0003\u0006d\b\u0006"+
		"\u0001\u0007\u0001\u0007\u0001\b\u0001\b\u0001\b\u0001\b\u0003\bl\b\b"+
		"\u0001\b\u0003\bo\b\b\u0001\t\u0001\t\u0001\t\u0005\tt\b\t\n\t\f\tw\t"+
		"\t\u0001\n\u0001\n\u0001\n\u0001\n\u0001\n\u0001\n\u0001\n\u0003\n\u0080"+
		"\b\n\u0001\u000b\u0001\u000b\u0001\u000b\u0003\u000b\u0085\b\u000b\u0001"+
		"\u000b\u0001\u000b\u0001\u000b\u0000\u0000\f\u0000\u0002\u0004\u0006\b"+
		"\n\f\u000e\u0010\u0012\u0014\u0016\u0000\u0001\u0001\u0000\u0006\b\u0097"+
		"\u0000\u001c\u0001\u0000\u0000\u0000\u00022\u0001\u0000\u0000\u0000\u0004"+
		"4\u0001\u0000\u0000\u0000\u0006>\u0001\u0000\u0000\u0000\bF\u0001\u0000"+
		"\u0000\u0000\nP\u0001\u0000\u0000\u0000\fc\u0001\u0000\u0000\u0000\u000e"+
		"e\u0001\u0000\u0000\u0000\u0010g\u0001\u0000\u0000\u0000\u0012p\u0001"+
		"\u0000\u0000\u0000\u0014\u007f\u0001\u0000\u0000\u0000\u0016\u0081\u0001"+
		"\u0000\u0000\u0000\u0018\u001b\u0003\u0004\u0002\u0000\u0019\u001b\u0003"+
		"\u0002\u0001\u0000\u001a\u0018\u0001\u0000\u0000\u0000\u001a\u0019\u0001"+
		"\u0000\u0000\u0000\u001b\u001e\u0001\u0000\u0000\u0000\u001c\u001a\u0001"+
		"\u0000\u0000\u0000\u001c\u001d\u0001\u0000\u0000\u0000\u001d\u001f\u0001"+
		"\u0000\u0000\u0000\u001e\u001c\u0001\u0000\u0000\u0000\u001f \u0005\u0000"+
		"\u0000\u0001 \u0001\u0001\u0000\u0000\u0000!#\u0005\u001d\u0000\u0000"+
		"\"$\u0003\u0012\t\u0000#\"\u0001\u0000\u0000\u0000#$\u0001\u0000\u0000"+
		"\u0000$%\u0001\u0000\u0000\u0000%)\u0005\t\u0000\u0000&(\u0003\u0004\u0002"+
		"\u0000\'&\u0001\u0000\u0000\u0000(+\u0001\u0000\u0000\u0000)\'\u0001\u0000"+
		"\u0000\u0000)*\u0001\u0000\u0000\u0000*,\u0001\u0000\u0000\u0000+)\u0001"+
		"\u0000\u0000\u0000,3\u0005\n\u0000\u0000-/\u0005\u001d\u0000\u0000.0\u0003"+
		"\u0012\t\u0000/.\u0001\u0000\u0000\u0000/0\u0001\u0000\u0000\u000001\u0001"+
		"\u0000\u0000\u000013\u0005\u0010\u0000\u00002!\u0001\u0000\u0000\u0000"+
		"2-\u0001\u0000\u0000\u00003\u0003\u0001\u0000\u0000\u000045\u0003\u0006"+
		"\u0003\u000059\u0005\t\u0000\u000068\u0003\u0010\b\u000076\u0001\u0000"+
		"\u0000\u00008;\u0001\u0000\u0000\u000097\u0001\u0000\u0000\u00009:\u0001"+
		"\u0000\u0000\u0000:<\u0001\u0000\u0000\u0000;9\u0001\u0000\u0000\u0000"+
		"<=\u0005\n\u0000\u0000=\u0005\u0001\u0000\u0000\u0000>C\u0003\b\u0004"+
		"\u0000?@\u0005\u0011\u0000\u0000@B\u0003\b\u0004\u0000A?\u0001\u0000\u0000"+
		"\u0000BE\u0001\u0000\u0000\u0000CA\u0001\u0000\u0000\u0000CD\u0001\u0000"+
		"\u0000\u0000D\u0007\u0001\u0000\u0000\u0000EC\u0001\u0000\u0000\u0000"+
		"FL\u0003\n\u0005\u0000GH\u0003\u000e\u0007\u0000HI\u0003\n\u0005\u0000"+
		"IK\u0001\u0000\u0000\u0000JG\u0001\u0000\u0000\u0000KN\u0001\u0000\u0000"+
		"\u0000LJ\u0001\u0000\u0000\u0000LM\u0001\u0000\u0000\u0000M\t\u0001\u0000"+
		"\u0000\u0000NL\u0001\u0000\u0000\u0000OQ\u0003\f\u0006\u0000PO\u0001\u0000"+
		"\u0000\u0000QR\u0001\u0000\u0000\u0000RP\u0001\u0000\u0000\u0000RS\u0001"+
		"\u0000\u0000\u0000S\u000b\u0001\u0000\u0000\u0000Td\u0005\u0019\u0000"+
		"\u0000UV\u0005\u0002\u0000\u0000Vd\u0005\u0019\u0000\u0000WX\u0005\u0001"+
		"\u0000\u0000Xd\u0005\u0019\u0000\u0000Yd\u0005\u0003\u0000\u0000Zd\u0005"+
		"\u0004\u0000\u0000[d\u0005\u0005\u0000\u0000\\]\u0005\r\u0000\u0000]`"+
		"\u0005\u0019\u0000\u0000^_\u0005\u0012\u0000\u0000_a\u0003\u0012\t\u0000"+
		"`^\u0001\u0000\u0000\u0000`a\u0001\u0000\u0000\u0000ab\u0001\u0000\u0000"+
		"\u0000bd\u0005\u000e\u0000\u0000cT\u0001\u0000\u0000\u0000cU\u0001\u0000"+
		"\u0000\u0000cW\u0001\u0000\u0000\u0000cY\u0001\u0000\u0000\u0000cZ\u0001"+
		"\u0000\u0000\u0000c[\u0001\u0000\u0000\u0000c\\\u0001\u0000\u0000\u0000"+
		"d\r\u0001\u0000\u0000\u0000ef\u0007\u0000\u0000\u0000f\u000f\u0001\u0000"+
		"\u0000\u0000gh\u0005\u0019\u0000\u0000hi\u0005\u000f\u0000\u0000ik\u0003"+
		"\u0012\t\u0000jl\u0005\u001b\u0000\u0000kj\u0001\u0000\u0000\u0000kl\u0001"+
		"\u0000\u0000\u0000ln\u0001\u0000\u0000\u0000mo\u0005\u0010\u0000\u0000"+
		"nm\u0001\u0000\u0000\u0000no\u0001\u0000\u0000\u0000o\u0011\u0001\u0000"+
		"\u0000\u0000pu\u0003\u0014\n\u0000qr\u0005\u0011\u0000\u0000rt\u0003\u0014"+
		"\n\u0000sq\u0001\u0000\u0000\u0000tw\u0001\u0000\u0000\u0000us\u0001\u0000"+
		"\u0000\u0000uv\u0001\u0000\u0000\u0000v\u0013\u0001\u0000\u0000\u0000"+
		"wu\u0001\u0000\u0000\u0000x\u0080\u0005\u0014\u0000\u0000y\u0080\u0005"+
		"\u0013\u0000\u0000z\u0080\u0005\u0015\u0000\u0000{\u0080\u0005\u0016\u0000"+
		"\u0000|\u0080\u0005\u0019\u0000\u0000}\u0080\u0003\u0016\u000b\u0000~"+
		"\u0080\u0005\u001c\u0000\u0000\u007fx\u0001\u0000\u0000\u0000\u007fy\u0001"+
		"\u0000\u0000\u0000\u007fz\u0001\u0000\u0000\u0000\u007f{\u0001\u0000\u0000"+
		"\u0000\u007f|\u0001\u0000\u0000\u0000\u007f}\u0001\u0000\u0000\u0000\u007f"+
		"~\u0001\u0000\u0000\u0000\u0080\u0015\u0001\u0000\u0000\u0000\u0081\u0082"+
		"\u0005\u0019\u0000\u0000\u0082\u0084\u0005\u000b\u0000\u0000\u0083\u0085"+
		"\u0003\u0012\t\u0000\u0084\u0083\u0001\u0000\u0000\u0000\u0084\u0085\u0001"+
		"\u0000\u0000\u0000\u0085\u0086\u0001\u0000\u0000\u0000\u0086\u0087\u0005"+
		"\f\u0000\u0000\u0087\u0017\u0001\u0000\u0000\u0000\u0011\u001a\u001c#"+
		")/29CLR`cknu\u007f\u0084";
	public static final ATN _ATN =
		new ATNDeserializer().deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
		for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
			_decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
		}
	}
}