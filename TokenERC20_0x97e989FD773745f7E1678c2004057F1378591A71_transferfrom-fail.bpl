// #LTLProperty: [](started(TokenERC20.transferFrom(from, to, value), from != to && (value > this.balanceOf[from] || value > this.allowance[from][msg.sender] || this.balanceOf[to] + value >= 0x10000000000000000000000000000000000000000000000000000000000000000) && value >= 0 && value < 0x10000000000000000000000000000000000000000000000000000000000000000 && this.balanceOf[to] >= 0 && this.balanceOf[to] < 0x10000000000000000000000000000000000000000000000000000000000000000 && this.balanceOf[from] >= 0 && this.balanceOf[from] < 0x10000000000000000000000000000000000000000000000000000000000000000 && this.allowance[from][msg.sender] >= 0 && this.allowance[from][msg.sender] < 0x10000000000000000000000000000000000000000000000000000000000000000) ==> <>(reverted(TokenERC20.transferFrom)))

type Ref = int;
type ContractName = int;
const unique null: Ref;
const unique tokenRecipient: ContractName;
const unique TokenERC20: ContractName;
function {:smtdefined "x"} ConstantToRef(x: int) returns (ret: Ref);
function BoogieRefToInt(x: Ref) returns (ret: int);
function {:bvbuiltin "mod"} modBpl(x: int, y: int) returns (ret: int);
function keccak256(x: int) returns (ret: int);
function abiEncodePacked1(x: int) returns (ret: int);
function _SumMapping_VeriSol(x: [Ref]int) returns (ret: int);
function abiEncodePacked2(x: int, y: int) returns (ret: int);
function abiEncodePacked1R(x: Ref) returns (ret: int);
function abiEncodePacked2R(x: Ref, y: int) returns (ret: int);
function {:smtdefined "((as const (Array Int Bool)) false)"} zeroRefboolArr() returns (ret: [Ref]bool);
function {:smtdefined "((as const (Array Int Int)) 0)"} zeroRefintArr() returns (ret: [Ref]int);
function {:smtdefined "((as const (Array Int (Array Int Int))) ((as const (Array Int Int)) 0))"} zeroRefRefintArr() returns (ret: [Ref][Ref]int);
var Balance: [Ref]int;
var DType: [Ref]ContractName;
var Alloc: [Ref]bool;
var balance_ADDR: [Ref]int;
var M_Ref_bool: [Ref][Ref]bool;
var sum_whitelisted0: [Ref]int;
var sum_admin1: [Ref]int;
var M_Ref_int: [Ref][Ref]int;
var sum_balanceOf2: [Ref]int;
var alloc_allowance_TokenERC20_lvl0: [Ref][Ref]bool;
function {:smtdefined "((as const (Array Int Int)) 0)"} zeroRefRefArr() returns (ret: [Ref]Ref);
var M_Ref_Ref: [Ref][Ref]Ref;
var sum_allowance3: [Ref]int;
var Length: [Ref]int;
var revert: bool;
var gas: int;
var now: int;
procedure {:inline 1} FreshRefGenerator__success() returns (newRef: Ref);
modifies Alloc;
var {:access "this.name=name_TokenERC20[this]"} name_TokenERC20: [Ref]int;
var {:access "this.symbol=symbol_TokenERC20[this]"} symbol_TokenERC20: [Ref]int;
var {:access "this.decimals=decimals_TokenERC20[this]"} decimals_TokenERC20: [Ref]int;
var {:access "this.owner=owner_TokenERC20[this]"} owner_TokenERC20: [Ref]Ref;
var {:access "this.totalSupply=totalSupply_TokenERC20[this]"} totalSupply_TokenERC20: [Ref]int;
var {:access "this.lockIn=lockIn_TokenERC20[this]"} lockIn_TokenERC20: [Ref]bool;
var {:access "this.whitelisted[i1]=whitelisted_TokenERC20[this][i1]"} {:sum "sum(this.whitelisted)=sum_whitelisted0[this]"} whitelisted_TokenERC20: [Ref][Ref]bool;
var {:access "this.admin[i1]=admin_TokenERC20[this][i1]"} {:sum "sum(this.admin)=sum_admin1[this]"} admin_TokenERC20: [Ref][Ref]bool;
var {:access "this.balanceOf[i1]=balanceOf_TokenERC20[this][i1]"} {:sum "sum(this.balanceOf)=sum_balanceOf2[this]"} balanceOf_TokenERC20: [Ref][Ref]int;
var {:access "this.allowance[i1][i2]=allowance_TokenERC20[this][i1][i2]"} {:sum "sum(this.allowance)=sum_allowance3[this]"} allowance_TokenERC20: [Ref][Ref][Ref]int;
procedure {:inline 1} _transfer~address~address~uint256_TokenERC20__success(this: Ref, msgsender_MSG: Ref, msgvalue_MSG: int, _from_s321: Ref, _to_s321: Ref, _value_s321: int);
modifies revert;
modifies sum_balanceOf2;
modifies balanceOf_TokenERC20;
procedure {:public} {:inline 1} transfer~address~uint256_TokenERC20(this: Ref, msgsender_MSG: Ref, msgvalue_MSG: int, _to_s336: Ref, _value_s336: int);
modifies revert;
modifies __tmp__Balance;
modifies __tmp__DType;
modifies __tmp__Alloc;
modifies __tmp__balance_ADDR;
modifies __tmp__M_Ref_bool;
modifies __tmp__sum_whitelisted0;
modifies __tmp__sum_admin1;
modifies __tmp__M_Ref_int;
modifies __tmp__sum_balanceOf2;
modifies __tmp__alloc_allowance_TokenERC20_lvl0;
modifies __tmp__M_Ref_Ref;
modifies __tmp__sum_allowance3;
modifies __tmp__Length;
modifies __tmp__now;
modifies __tmp__name_TokenERC20;
modifies __tmp__symbol_TokenERC20;
modifies __tmp__decimals_TokenERC20;
modifies __tmp__owner_TokenERC20;
modifies __tmp__totalSupply_TokenERC20;
modifies __tmp__lockIn_TokenERC20;
modifies __tmp__whitelisted_TokenERC20;
modifies __tmp__admin_TokenERC20;
modifies __tmp__balanceOf_TokenERC20;
modifies __tmp__allowance_TokenERC20;
modifies sum_balanceOf2;
modifies balanceOf_TokenERC20;
implementation transfer~address~uint256_TokenERC20(this: Ref, msgsender_MSG: Ref, msgvalue_MSG: int, _to_s336: Ref, _value_s336: int)
{
var __exception: bool;
havoc __exception;
revert := false;
if (__exception) {
__tmp__Balance := Balance;
__tmp__DType := DType;
__tmp__Alloc := Alloc;
__tmp__balance_ADDR := balance_ADDR;
__tmp__M_Ref_bool := M_Ref_bool;
__tmp__sum_whitelisted0 := sum_whitelisted0;
__tmp__sum_admin1 := sum_admin1;
__tmp__M_Ref_int := M_Ref_int;
__tmp__sum_balanceOf2 := sum_balanceOf2;
__tmp__alloc_allowance_TokenERC20_lvl0 := alloc_allowance_TokenERC20_lvl0;
__tmp__M_Ref_Ref := M_Ref_Ref;
__tmp__sum_allowance3 := sum_allowance3;
__tmp__Length := Length;
__tmp__now := now;
__tmp__name_TokenERC20 := name_TokenERC20;
__tmp__symbol_TokenERC20 := symbol_TokenERC20;
__tmp__decimals_TokenERC20 := decimals_TokenERC20;
__tmp__owner_TokenERC20 := owner_TokenERC20;
__tmp__totalSupply_TokenERC20 := totalSupply_TokenERC20;
__tmp__lockIn_TokenERC20 := lockIn_TokenERC20;
__tmp__whitelisted_TokenERC20 := whitelisted_TokenERC20;
__tmp__admin_TokenERC20 := admin_TokenERC20;
__tmp__balanceOf_TokenERC20 := balanceOf_TokenERC20;
__tmp__allowance_TokenERC20 := allowance_TokenERC20;
call transfer~address~uint256_TokenERC20__fail(this, msgsender_MSG, msgvalue_MSG, _to_s336, _value_s336);
assume ((revert) || ((gas) < (0)));
} else {
call transfer~address~uint256_TokenERC20__success(this, msgsender_MSG, msgvalue_MSG, _to_s336, _value_s336);
assume ((!(revert)) && ((gas) >= (0)));
}
}

procedure {:public} {:inline 1} transferFrom~address~address~uint256_TokenERC20(this: Ref, msgsender_MSG: Ref, msgvalue_MSG: int, _from_s376: Ref, _to_s376: Ref, _value_s376: int) returns (success_s376: bool);
modifies revert;
modifies __tmp__Balance;
modifies __tmp__DType;
modifies __tmp__Alloc;
modifies __tmp__balance_ADDR;
modifies __tmp__M_Ref_bool;
modifies __tmp__sum_whitelisted0;
modifies __tmp__sum_admin1;
modifies __tmp__M_Ref_int;
modifies __tmp__sum_balanceOf2;
modifies __tmp__alloc_allowance_TokenERC20_lvl0;
modifies __tmp__M_Ref_Ref;
modifies __tmp__sum_allowance3;
modifies __tmp__Length;
modifies __tmp__now;
modifies __tmp__name_TokenERC20;
modifies __tmp__symbol_TokenERC20;
modifies __tmp__decimals_TokenERC20;
modifies __tmp__owner_TokenERC20;
modifies __tmp__totalSupply_TokenERC20;
modifies __tmp__lockIn_TokenERC20;
modifies __tmp__whitelisted_TokenERC20;
modifies __tmp__admin_TokenERC20;
modifies __tmp__balanceOf_TokenERC20;
modifies __tmp__allowance_TokenERC20;
modifies sum_allowance3;
modifies allowance_TokenERC20;
modifies sum_balanceOf2;
modifies balanceOf_TokenERC20;
implementation transferFrom~address~address~uint256_TokenERC20(this: Ref, msgsender_MSG: Ref, msgvalue_MSG: int, _from_s376: Ref, _to_s376: Ref, _value_s376: int) returns (success_s376: bool)
{
var __exception: bool;
havoc __exception;
revert := false;
if (__exception) {
__tmp__Balance := Balance;
__tmp__DType := DType;
__tmp__Alloc := Alloc;
__tmp__balance_ADDR := balance_ADDR;
__tmp__M_Ref_bool := M_Ref_bool;
__tmp__sum_whitelisted0 := sum_whitelisted0;
__tmp__sum_admin1 := sum_admin1;
__tmp__M_Ref_int := M_Ref_int;
__tmp__sum_balanceOf2 := sum_balanceOf2;
__tmp__alloc_allowance_TokenERC20_lvl0 := alloc_allowance_TokenERC20_lvl0;
__tmp__M_Ref_Ref := M_Ref_Ref;
__tmp__sum_allowance3 := sum_allowance3;
__tmp__Length := Length;
__tmp__now := now;
__tmp__name_TokenERC20 := name_TokenERC20;
__tmp__symbol_TokenERC20 := symbol_TokenERC20;
__tmp__decimals_TokenERC20 := decimals_TokenERC20;
__tmp__owner_TokenERC20 := owner_TokenERC20;
__tmp__totalSupply_TokenERC20 := totalSupply_TokenERC20;
__tmp__lockIn_TokenERC20 := lockIn_TokenERC20;
__tmp__whitelisted_TokenERC20 := whitelisted_TokenERC20;
__tmp__admin_TokenERC20 := admin_TokenERC20;
__tmp__balanceOf_TokenERC20 := balanceOf_TokenERC20;
__tmp__allowance_TokenERC20 := allowance_TokenERC20;
call success_s376 := transferFrom~address~address~uint256_TokenERC20__fail(this, msgsender_MSG, msgvalue_MSG, _from_s376, _to_s376, _value_s376);
assume ((revert) || ((gas) < (0)));
} else {
call success_s376 := transferFrom~address~address~uint256_TokenERC20__success(this, msgsender_MSG, msgvalue_MSG, _from_s376, _to_s376, _value_s376);
assume ((!(revert)) && ((gas) >= (0)));
}
}

procedure {:public} {:inline 1} approve~address~uint256_TokenERC20(this: Ref, msgsender_MSG: Ref, msgvalue_MSG: int, _spender_s404: Ref, _value_s404: int) returns (success_s404: bool);
modifies revert;
modifies __tmp__Balance;
modifies __tmp__DType;
modifies __tmp__Alloc;
modifies __tmp__balance_ADDR;
modifies __tmp__M_Ref_bool;
modifies __tmp__sum_whitelisted0;
modifies __tmp__sum_admin1;
modifies __tmp__M_Ref_int;
modifies __tmp__sum_balanceOf2;
modifies __tmp__alloc_allowance_TokenERC20_lvl0;
modifies __tmp__M_Ref_Ref;
modifies __tmp__sum_allowance3;
modifies __tmp__Length;
modifies __tmp__now;
modifies __tmp__name_TokenERC20;
modifies __tmp__symbol_TokenERC20;
modifies __tmp__decimals_TokenERC20;
modifies __tmp__owner_TokenERC20;
modifies __tmp__totalSupply_TokenERC20;
modifies __tmp__lockIn_TokenERC20;
modifies __tmp__whitelisted_TokenERC20;
modifies __tmp__admin_TokenERC20;
modifies __tmp__balanceOf_TokenERC20;
modifies __tmp__allowance_TokenERC20;
modifies sum_allowance3;
modifies allowance_TokenERC20;
implementation approve~address~uint256_TokenERC20(this: Ref, msgsender_MSG: Ref, msgvalue_MSG: int, _spender_s404: Ref, _value_s404: int) returns (success_s404: bool)
{
var __exception: bool;
havoc __exception;
revert := false;
if (__exception) {
__tmp__Balance := Balance;
__tmp__DType := DType;
__tmp__Alloc := Alloc;
__tmp__balance_ADDR := balance_ADDR;
__tmp__M_Ref_bool := M_Ref_bool;
__tmp__sum_whitelisted0 := sum_whitelisted0;
__tmp__sum_admin1 := sum_admin1;
__tmp__M_Ref_int := M_Ref_int;
__tmp__sum_balanceOf2 := sum_balanceOf2;
__tmp__alloc_allowance_TokenERC20_lvl0 := alloc_allowance_TokenERC20_lvl0;
__tmp__M_Ref_Ref := M_Ref_Ref;
__tmp__sum_allowance3 := sum_allowance3;
__tmp__Length := Length;
__tmp__now := now;
__tmp__name_TokenERC20 := name_TokenERC20;
__tmp__symbol_TokenERC20 := symbol_TokenERC20;
__tmp__decimals_TokenERC20 := decimals_TokenERC20;
__tmp__owner_TokenERC20 := owner_TokenERC20;
__tmp__totalSupply_TokenERC20 := totalSupply_TokenERC20;
__tmp__lockIn_TokenERC20 := lockIn_TokenERC20;
__tmp__whitelisted_TokenERC20 := whitelisted_TokenERC20;
__tmp__admin_TokenERC20 := admin_TokenERC20;
__tmp__balanceOf_TokenERC20 := balanceOf_TokenERC20;
__tmp__allowance_TokenERC20 := allowance_TokenERC20;
call success_s404 := approve~address~uint256_TokenERC20__fail(this, msgsender_MSG, msgvalue_MSG, _spender_s404, _value_s404);
assume ((revert) || ((gas) < (0)));
} else {
call success_s404 := approve~address~uint256_TokenERC20__success(this, msgsender_MSG, msgvalue_MSG, _spender_s404, _value_s404);
assume ((!(revert)) && ((gas) >= (0)));
}
}

procedure {:public} {:inline 1} burn~uint256_TokenERC20(this: Ref, msgsender_MSG: Ref, msgvalue_MSG: int, _value_s478: int) returns (success_s478: bool);
modifies revert;
modifies __tmp__Balance;
modifies __tmp__DType;
modifies __tmp__Alloc;
modifies __tmp__balance_ADDR;
modifies __tmp__M_Ref_bool;
modifies __tmp__sum_whitelisted0;
modifies __tmp__sum_admin1;
modifies __tmp__M_Ref_int;
modifies __tmp__sum_balanceOf2;
modifies __tmp__alloc_allowance_TokenERC20_lvl0;
modifies __tmp__M_Ref_Ref;
modifies __tmp__sum_allowance3;
modifies __tmp__Length;
modifies __tmp__now;
modifies __tmp__name_TokenERC20;
modifies __tmp__symbol_TokenERC20;
modifies __tmp__decimals_TokenERC20;
modifies __tmp__owner_TokenERC20;
modifies __tmp__totalSupply_TokenERC20;
modifies __tmp__lockIn_TokenERC20;
modifies __tmp__whitelisted_TokenERC20;
modifies __tmp__admin_TokenERC20;
modifies __tmp__balanceOf_TokenERC20;
modifies __tmp__allowance_TokenERC20;
modifies sum_balanceOf2;
modifies balanceOf_TokenERC20;
modifies totalSupply_TokenERC20;
implementation burn~uint256_TokenERC20(this: Ref, msgsender_MSG: Ref, msgvalue_MSG: int, _value_s478: int) returns (success_s478: bool)
{
var __exception: bool;
havoc __exception;
revert := false;
if (__exception) {
__tmp__Balance := Balance;
__tmp__DType := DType;
__tmp__Alloc := Alloc;
__tmp__balance_ADDR := balance_ADDR;
__tmp__M_Ref_bool := M_Ref_bool;
__tmp__sum_whitelisted0 := sum_whitelisted0;
__tmp__sum_admin1 := sum_admin1;
__tmp__M_Ref_int := M_Ref_int;
__tmp__sum_balanceOf2 := sum_balanceOf2;
__tmp__alloc_allowance_TokenERC20_lvl0 := alloc_allowance_TokenERC20_lvl0;
__tmp__M_Ref_Ref := M_Ref_Ref;
__tmp__sum_allowance3 := sum_allowance3;
__tmp__Length := Length;
__tmp__now := now;
__tmp__name_TokenERC20 := name_TokenERC20;
__tmp__symbol_TokenERC20 := symbol_TokenERC20;
__tmp__decimals_TokenERC20 := decimals_TokenERC20;
__tmp__owner_TokenERC20 := owner_TokenERC20;
__tmp__totalSupply_TokenERC20 := totalSupply_TokenERC20;
__tmp__lockIn_TokenERC20 := lockIn_TokenERC20;
__tmp__whitelisted_TokenERC20 := whitelisted_TokenERC20;
__tmp__admin_TokenERC20 := admin_TokenERC20;
__tmp__balanceOf_TokenERC20 := balanceOf_TokenERC20;
__tmp__allowance_TokenERC20 := allowance_TokenERC20;
call success_s478 := burn~uint256_TokenERC20__fail(this, msgsender_MSG, msgvalue_MSG, _value_s478);
assume ((revert) || ((gas) < (0)));
} else {
call success_s478 := burn~uint256_TokenERC20__success(this, msgsender_MSG, msgvalue_MSG, _value_s478);
assume ((!(revert)) && ((gas) >= (0)));
}
}

procedure {:public} {:inline 1} burnFrom~address~uint256_TokenERC20(this: Ref, msgsender_MSG: Ref, msgvalue_MSG: int, _from_s533: Ref, _value_s533: int) returns (success_s533: bool);
modifies revert;
modifies __tmp__Balance;
modifies __tmp__DType;
modifies __tmp__Alloc;
modifies __tmp__balance_ADDR;
modifies __tmp__M_Ref_bool;
modifies __tmp__sum_whitelisted0;
modifies __tmp__sum_admin1;
modifies __tmp__M_Ref_int;
modifies __tmp__sum_balanceOf2;
modifies __tmp__alloc_allowance_TokenERC20_lvl0;
modifies __tmp__M_Ref_Ref;
modifies __tmp__sum_allowance3;
modifies __tmp__Length;
modifies __tmp__now;
modifies __tmp__name_TokenERC20;
modifies __tmp__symbol_TokenERC20;
modifies __tmp__decimals_TokenERC20;
modifies __tmp__owner_TokenERC20;
modifies __tmp__totalSupply_TokenERC20;
modifies __tmp__lockIn_TokenERC20;
modifies __tmp__whitelisted_TokenERC20;
modifies __tmp__admin_TokenERC20;
modifies __tmp__balanceOf_TokenERC20;
modifies __tmp__allowance_TokenERC20;
modifies sum_balanceOf2;
modifies balanceOf_TokenERC20;
modifies sum_allowance3;
modifies allowance_TokenERC20;
modifies totalSupply_TokenERC20;
implementation burnFrom~address~uint256_TokenERC20(this: Ref, msgsender_MSG: Ref, msgvalue_MSG: int, _from_s533: Ref, _value_s533: int) returns (success_s533: bool)
{
var __exception: bool;
havoc __exception;
revert := false;
if (__exception) {
__tmp__Balance := Balance;
__tmp__DType := DType;
__tmp__Alloc := Alloc;
__tmp__balance_ADDR := balance_ADDR;
__tmp__M_Ref_bool := M_Ref_bool;
__tmp__sum_whitelisted0 := sum_whitelisted0;
__tmp__sum_admin1 := sum_admin1;
__tmp__M_Ref_int := M_Ref_int;
__tmp__sum_balanceOf2 := sum_balanceOf2;
__tmp__alloc_allowance_TokenERC20_lvl0 := alloc_allowance_TokenERC20_lvl0;
__tmp__M_Ref_Ref := M_Ref_Ref;
__tmp__sum_allowance3 := sum_allowance3;
__tmp__Length := Length;
__tmp__now := now;
__tmp__name_TokenERC20 := name_TokenERC20;
__tmp__symbol_TokenERC20 := symbol_TokenERC20;
__tmp__decimals_TokenERC20 := decimals_TokenERC20;
__tmp__owner_TokenERC20 := owner_TokenERC20;
__tmp__totalSupply_TokenERC20 := totalSupply_TokenERC20;
__tmp__lockIn_TokenERC20 := lockIn_TokenERC20;
__tmp__whitelisted_TokenERC20 := whitelisted_TokenERC20;
__tmp__admin_TokenERC20 := admin_TokenERC20;
__tmp__balanceOf_TokenERC20 := balanceOf_TokenERC20;
__tmp__allowance_TokenERC20 := allowance_TokenERC20;
call success_s533 := burnFrom~address~uint256_TokenERC20__fail(this, msgsender_MSG, msgvalue_MSG, _from_s533, _value_s533);
assume ((revert) || ((gas) < (0)));
} else {
call success_s533 := burnFrom~address~uint256_TokenERC20__success(this, msgsender_MSG, msgvalue_MSG, _from_s533, _value_s533);
assume ((!(revert)) && ((gas) >= (0)));
}
}

procedure {:public} {:inline 1} totalSupply_TokenERC20(this: Ref, msgsender_MSG: Ref, msgvalue_MSG: int) returns (__ret_0_: int);
modifies revert;
modifies __tmp__Balance;
modifies __tmp__DType;
modifies __tmp__Alloc;
modifies __tmp__balance_ADDR;
modifies __tmp__M_Ref_bool;
modifies __tmp__sum_whitelisted0;
modifies __tmp__sum_admin1;
modifies __tmp__M_Ref_int;
modifies __tmp__sum_balanceOf2;
modifies __tmp__alloc_allowance_TokenERC20_lvl0;
modifies __tmp__M_Ref_Ref;
modifies __tmp__sum_allowance3;
modifies __tmp__Length;
modifies __tmp__now;
modifies __tmp__name_TokenERC20;
modifies __tmp__symbol_TokenERC20;
modifies __tmp__decimals_TokenERC20;
modifies __tmp__owner_TokenERC20;
modifies __tmp__totalSupply_TokenERC20;
modifies __tmp__lockIn_TokenERC20;
modifies __tmp__whitelisted_TokenERC20;
modifies __tmp__admin_TokenERC20;
modifies __tmp__balanceOf_TokenERC20;
modifies __tmp__allowance_TokenERC20;
implementation totalSupply_TokenERC20(this: Ref, msgsender_MSG: Ref, msgvalue_MSG: int) returns (__ret_0_: int)
{
var __exception: bool;
havoc __exception;
revert := false;
if (__exception) {
__tmp__Balance := Balance;
__tmp__DType := DType;
__tmp__Alloc := Alloc;
__tmp__balance_ADDR := balance_ADDR;
__tmp__M_Ref_bool := M_Ref_bool;
__tmp__sum_whitelisted0 := sum_whitelisted0;
__tmp__sum_admin1 := sum_admin1;
__tmp__M_Ref_int := M_Ref_int;
__tmp__sum_balanceOf2 := sum_balanceOf2;
__tmp__alloc_allowance_TokenERC20_lvl0 := alloc_allowance_TokenERC20_lvl0;
__tmp__M_Ref_Ref := M_Ref_Ref;
__tmp__sum_allowance3 := sum_allowance3;
__tmp__Length := Length;
__tmp__now := now;
__tmp__name_TokenERC20 := name_TokenERC20;
__tmp__symbol_TokenERC20 := symbol_TokenERC20;
__tmp__decimals_TokenERC20 := decimals_TokenERC20;
__tmp__owner_TokenERC20 := owner_TokenERC20;
__tmp__totalSupply_TokenERC20 := totalSupply_TokenERC20;
__tmp__lockIn_TokenERC20 := lockIn_TokenERC20;
__tmp__whitelisted_TokenERC20 := whitelisted_TokenERC20;
__tmp__admin_TokenERC20 := admin_TokenERC20;
__tmp__balanceOf_TokenERC20 := balanceOf_TokenERC20;
__tmp__allowance_TokenERC20 := allowance_TokenERC20;
call __ret_0_ := totalSupply_TokenERC20__fail(this, msgsender_MSG, msgvalue_MSG);
assume ((revert) || ((gas) < (0)));
} else {
call __ret_0_ := totalSupply_TokenERC20__success(this, msgsender_MSG, msgvalue_MSG);
assume ((!(revert)) && ((gas) >= (0)));
}
}

procedure {:public} {:inline 1} balanceOf~address_TokenERC20(this: Ref, msgsender_MSG: Ref, msgvalue_MSG: int, arg536_s0: Ref) returns (__ret_0_: int);
modifies revert;
modifies __tmp__Balance;
modifies __tmp__DType;
modifies __tmp__Alloc;
modifies __tmp__balance_ADDR;
modifies __tmp__M_Ref_bool;
modifies __tmp__sum_whitelisted0;
modifies __tmp__sum_admin1;
modifies __tmp__M_Ref_int;
modifies __tmp__sum_balanceOf2;
modifies __tmp__alloc_allowance_TokenERC20_lvl0;
modifies __tmp__M_Ref_Ref;
modifies __tmp__sum_allowance3;
modifies __tmp__Length;
modifies __tmp__now;
modifies __tmp__name_TokenERC20;
modifies __tmp__symbol_TokenERC20;
modifies __tmp__decimals_TokenERC20;
modifies __tmp__owner_TokenERC20;
modifies __tmp__totalSupply_TokenERC20;
modifies __tmp__lockIn_TokenERC20;
modifies __tmp__whitelisted_TokenERC20;
modifies __tmp__admin_TokenERC20;
modifies __tmp__balanceOf_TokenERC20;
modifies __tmp__allowance_TokenERC20;
implementation balanceOf~address_TokenERC20(this: Ref, msgsender_MSG: Ref, msgvalue_MSG: int, arg536_s0: Ref) returns (__ret_0_: int)
{
var __exception: bool;
havoc __exception;
revert := false;
if (__exception) {
__tmp__Balance := Balance;
__tmp__DType := DType;
__tmp__Alloc := Alloc;
__tmp__balance_ADDR := balance_ADDR;
__tmp__M_Ref_bool := M_Ref_bool;
__tmp__sum_whitelisted0 := sum_whitelisted0;
__tmp__sum_admin1 := sum_admin1;
__tmp__M_Ref_int := M_Ref_int;
__tmp__sum_balanceOf2 := sum_balanceOf2;
__tmp__alloc_allowance_TokenERC20_lvl0 := alloc_allowance_TokenERC20_lvl0;
__tmp__M_Ref_Ref := M_Ref_Ref;
__tmp__sum_allowance3 := sum_allowance3;
__tmp__Length := Length;
__tmp__now := now;
__tmp__name_TokenERC20 := name_TokenERC20;
__tmp__symbol_TokenERC20 := symbol_TokenERC20;
__tmp__decimals_TokenERC20 := decimals_TokenERC20;
__tmp__owner_TokenERC20 := owner_TokenERC20;
__tmp__totalSupply_TokenERC20 := totalSupply_TokenERC20;
__tmp__lockIn_TokenERC20 := lockIn_TokenERC20;
__tmp__whitelisted_TokenERC20 := whitelisted_TokenERC20;
__tmp__admin_TokenERC20 := admin_TokenERC20;
__tmp__balanceOf_TokenERC20 := balanceOf_TokenERC20;
__tmp__allowance_TokenERC20 := allowance_TokenERC20;
call __ret_0_ := balanceOf~address_TokenERC20__fail(this, msgsender_MSG, msgvalue_MSG, arg536_s0);
assume ((revert) || ((gas) < (0)));
} else {
call __ret_0_ := balanceOf~address_TokenERC20__success(this, msgsender_MSG, msgvalue_MSG, arg536_s0);
assume ((!(revert)) && ((gas) >= (0)));
}
}

procedure {:public} {:inline 1} allowance~address~address_TokenERC20(this: Ref, msgsender_MSG: Ref, msgvalue_MSG: int, arg537_s0: Ref, arg538_s0: Ref) returns (__ret_0_: int);
modifies revert;
modifies __tmp__Balance;
modifies __tmp__DType;
modifies __tmp__Alloc;
modifies __tmp__balance_ADDR;
modifies __tmp__M_Ref_bool;
modifies __tmp__sum_whitelisted0;
modifies __tmp__sum_admin1;
modifies __tmp__M_Ref_int;
modifies __tmp__sum_balanceOf2;
modifies __tmp__alloc_allowance_TokenERC20_lvl0;
modifies __tmp__M_Ref_Ref;
modifies __tmp__sum_allowance3;
modifies __tmp__Length;
modifies __tmp__now;
modifies __tmp__name_TokenERC20;
modifies __tmp__symbol_TokenERC20;
modifies __tmp__decimals_TokenERC20;
modifies __tmp__owner_TokenERC20;
modifies __tmp__totalSupply_TokenERC20;
modifies __tmp__lockIn_TokenERC20;
modifies __tmp__whitelisted_TokenERC20;
modifies __tmp__admin_TokenERC20;
modifies __tmp__balanceOf_TokenERC20;
modifies __tmp__allowance_TokenERC20;
implementation allowance~address~address_TokenERC20(this: Ref, msgsender_MSG: Ref, msgvalue_MSG: int, arg537_s0: Ref, arg538_s0: Ref) returns (__ret_0_: int)
{
var __exception: bool;
havoc __exception;
revert := false;
if (__exception) {
__tmp__Balance := Balance;
__tmp__DType := DType;
__tmp__Alloc := Alloc;
__tmp__balance_ADDR := balance_ADDR;
__tmp__M_Ref_bool := M_Ref_bool;
__tmp__sum_whitelisted0 := sum_whitelisted0;
__tmp__sum_admin1 := sum_admin1;
__tmp__M_Ref_int := M_Ref_int;
__tmp__sum_balanceOf2 := sum_balanceOf2;
__tmp__alloc_allowance_TokenERC20_lvl0 := alloc_allowance_TokenERC20_lvl0;
__tmp__M_Ref_Ref := M_Ref_Ref;
__tmp__sum_allowance3 := sum_allowance3;
__tmp__Length := Length;
__tmp__now := now;
__tmp__name_TokenERC20 := name_TokenERC20;
__tmp__symbol_TokenERC20 := symbol_TokenERC20;
__tmp__decimals_TokenERC20 := decimals_TokenERC20;
__tmp__owner_TokenERC20 := owner_TokenERC20;
__tmp__totalSupply_TokenERC20 := totalSupply_TokenERC20;
__tmp__lockIn_TokenERC20 := lockIn_TokenERC20;
__tmp__whitelisted_TokenERC20 := whitelisted_TokenERC20;
__tmp__admin_TokenERC20 := admin_TokenERC20;
__tmp__balanceOf_TokenERC20 := balanceOf_TokenERC20;
__tmp__allowance_TokenERC20 := allowance_TokenERC20;
call __ret_0_ := allowance~address~address_TokenERC20__fail(this, msgsender_MSG, msgvalue_MSG, arg537_s0, arg538_s0);
assume ((revert) || ((gas) < (0)));
} else {
call __ret_0_ := allowance~address~address_TokenERC20__success(this, msgsender_MSG, msgvalue_MSG, arg537_s0, arg538_s0);
assume ((!(revert)) && ((gas) >= (0)));
}
}

procedure {:inline 1} FallbackDispatch__success(from: Ref, to: Ref, amount: int);
modifies revert;
modifies gas;
modifies sum_balanceOf2;
modifies balanceOf_TokenERC20;
modifies sum_allowance3;
modifies allowance_TokenERC20;
modifies totalSupply_TokenERC20;
procedure {:inline 1} Fallback_UnknownType__success(from: Ref, to: Ref, amount: int);
modifies revert;
modifies gas;
modifies sum_balanceOf2;
modifies balanceOf_TokenERC20;
modifies sum_allowance3;
modifies allowance_TokenERC20;
modifies totalSupply_TokenERC20;
procedure {:inline 1} send__success(from: Ref, to: Ref, amount: int) returns (success: bool);
modifies __tmp__Balance;
modifies __tmp__DType;
modifies __tmp__Alloc;
modifies __tmp__balance_ADDR;
modifies __tmp__M_Ref_bool;
modifies __tmp__sum_whitelisted0;
modifies __tmp__sum_admin1;
modifies __tmp__M_Ref_int;
modifies __tmp__sum_balanceOf2;
modifies __tmp__alloc_allowance_TokenERC20_lvl0;
modifies __tmp__M_Ref_Ref;
modifies __tmp__sum_allowance3;
modifies __tmp__Length;
modifies __tmp__now;
modifies __tmp__name_TokenERC20;
modifies __tmp__symbol_TokenERC20;
modifies __tmp__decimals_TokenERC20;
modifies __tmp__owner_TokenERC20;
modifies __tmp__totalSupply_TokenERC20;
modifies __tmp__lockIn_TokenERC20;
modifies __tmp__whitelisted_TokenERC20;
modifies __tmp__admin_TokenERC20;
modifies __tmp__balanceOf_TokenERC20;
modifies __tmp__allowance_TokenERC20;
modifies revert;
modifies Balance;
modifies gas;
modifies sum_balanceOf2;
modifies balanceOf_TokenERC20;
modifies sum_allowance3;
modifies allowance_TokenERC20;
modifies totalSupply_TokenERC20;
procedure CorralChoice_tokenRecipient(this: Ref);
modifies gas;
modifies now;
procedure CorralEntry_tokenRecipient();
modifies Alloc;
modifies gas;
modifies now;
procedure CorralChoice_TokenERC20(this: Ref);
modifies gas;
modifies now;
modifies revert;
modifies __tmp__Balance;
modifies __tmp__DType;
modifies __tmp__Alloc;
modifies __tmp__balance_ADDR;
modifies __tmp__M_Ref_bool;
modifies __tmp__sum_whitelisted0;
modifies __tmp__sum_admin1;
modifies __tmp__M_Ref_int;
modifies __tmp__sum_balanceOf2;
modifies __tmp__alloc_allowance_TokenERC20_lvl0;
modifies __tmp__M_Ref_Ref;
modifies __tmp__sum_allowance3;
modifies __tmp__Length;
modifies __tmp__now;
modifies __tmp__name_TokenERC20;
modifies __tmp__symbol_TokenERC20;
modifies __tmp__decimals_TokenERC20;
modifies __tmp__owner_TokenERC20;
modifies __tmp__totalSupply_TokenERC20;
modifies __tmp__lockIn_TokenERC20;
modifies __tmp__whitelisted_TokenERC20;
modifies __tmp__admin_TokenERC20;
modifies __tmp__balanceOf_TokenERC20;
modifies __tmp__allowance_TokenERC20;
modifies sum_allowance3;
modifies allowance_TokenERC20;
modifies sum_balanceOf2;
modifies balanceOf_TokenERC20;
modifies totalSupply_TokenERC20;
procedure main();
modifies Alloc;
modifies gas;
modifies now;
modifies revert;
modifies __tmp__Balance;
modifies __tmp__DType;
modifies __tmp__Alloc;
modifies __tmp__balance_ADDR;
modifies __tmp__M_Ref_bool;
modifies __tmp__sum_whitelisted0;
modifies __tmp__sum_admin1;
modifies __tmp__M_Ref_int;
modifies __tmp__sum_balanceOf2;
modifies __tmp__alloc_allowance_TokenERC20_lvl0;
modifies __tmp__M_Ref_Ref;
modifies __tmp__sum_allowance3;
modifies __tmp__Length;
modifies __tmp__now;
modifies __tmp__name_TokenERC20;
modifies __tmp__symbol_TokenERC20;
modifies __tmp__decimals_TokenERC20;
modifies __tmp__owner_TokenERC20;
modifies __tmp__totalSupply_TokenERC20;
modifies __tmp__lockIn_TokenERC20;
modifies __tmp__whitelisted_TokenERC20;
modifies __tmp__admin_TokenERC20;
modifies __tmp__balanceOf_TokenERC20;
modifies __tmp__allowance_TokenERC20;
modifies sum_allowance3;
modifies allowance_TokenERC20;
modifies sum_balanceOf2;
modifies balanceOf_TokenERC20;
modifies totalSupply_TokenERC20;
var __tmp__Balance: [Ref]int;
var __tmp__DType: [Ref]ContractName;
var __tmp__Alloc: [Ref]bool;
var __tmp__balance_ADDR: [Ref]int;
var __tmp__M_Ref_bool: [Ref][Ref]bool;
var __tmp__sum_whitelisted0: [Ref]int;
var __tmp__sum_admin1: [Ref]int;
var __tmp__M_Ref_int: [Ref][Ref]int;
var __tmp__sum_balanceOf2: [Ref]int;
var __tmp__alloc_allowance_TokenERC20_lvl0: [Ref][Ref]bool;
var __tmp__M_Ref_Ref: [Ref][Ref]Ref;
var __tmp__sum_allowance3: [Ref]int;
var __tmp__Length: [Ref]int;
var __tmp__now: int;
var __tmp__name_TokenERC20: [Ref]int;
var __tmp__symbol_TokenERC20: [Ref]int;
var __tmp__decimals_TokenERC20: [Ref]int;
var __tmp__owner_TokenERC20: [Ref]Ref;
var __tmp__totalSupply_TokenERC20: [Ref]int;
var __tmp__lockIn_TokenERC20: [Ref]bool;
var __tmp__whitelisted_TokenERC20: [Ref][Ref]bool;
var __tmp__admin_TokenERC20: [Ref][Ref]bool;
var __tmp__balanceOf_TokenERC20: [Ref][Ref]int;
var __tmp__allowance_TokenERC20: [Ref][Ref][Ref]int;
procedure {:inline 1} FreshRefGenerator__fail() returns (newRef: Ref);
modifies __tmp__Alloc;
procedure {:inline 1} _transfer~address~address~uint256_TokenERC20__fail(this: Ref, msgsender_MSG: Ref, msgvalue_MSG: int, _from_s321: Ref, _to_s321: Ref, _value_s321: int);
modifies revert;
modifies __tmp__sum_balanceOf2;
modifies __tmp__balanceOf_TokenERC20;
procedure {:inline 1} transfer~address~uint256_TokenERC20__success(this: Ref, msgsender_MSG: Ref, msgvalue_MSG: int, _to_s336: Ref, _value_s336: int);
modifies revert;
modifies sum_balanceOf2;
modifies balanceOf_TokenERC20;
procedure {:inline 1} transfer~address~uint256_TokenERC20__fail(this: Ref, msgsender_MSG: Ref, msgvalue_MSG: int, _to_s336: Ref, _value_s336: int);
modifies revert;
modifies __tmp__sum_balanceOf2;
modifies __tmp__balanceOf_TokenERC20;
procedure {:inline 1} transferFrom~address~address~uint256_TokenERC20__success(this: Ref, msgsender_MSG: Ref, msgvalue_MSG: int, _from_s376: Ref, _to_s376: Ref, _value_s376: int) returns (success_s376: bool);
modifies revert;
modifies sum_allowance3;
modifies allowance_TokenERC20;
modifies sum_balanceOf2;
modifies balanceOf_TokenERC20;
procedure {:inline 1} transferFrom~address~address~uint256_TokenERC20__fail(this: Ref, msgsender_MSG: Ref, msgvalue_MSG: int, _from_s376: Ref, _to_s376: Ref, _value_s376: int) returns (success_s376: bool);
modifies revert;
modifies __tmp__sum_allowance3;
modifies __tmp__allowance_TokenERC20;
modifies __tmp__sum_balanceOf2;
modifies __tmp__balanceOf_TokenERC20;
procedure {:inline 1} approve~address~uint256_TokenERC20__success(this: Ref, msgsender_MSG: Ref, msgvalue_MSG: int, _spender_s404: Ref, _value_s404: int) returns (success_s404: bool);
modifies sum_allowance3;
modifies allowance_TokenERC20;
procedure {:inline 1} approve~address~uint256_TokenERC20__fail(this: Ref, msgsender_MSG: Ref, msgvalue_MSG: int, _spender_s404: Ref, _value_s404: int) returns (success_s404: bool);
modifies __tmp__sum_allowance3;
modifies __tmp__allowance_TokenERC20;
procedure {:inline 1} burn~uint256_TokenERC20__success(this: Ref, msgsender_MSG: Ref, msgvalue_MSG: int, _value_s478: int) returns (success_s478: bool);
modifies revert;
modifies sum_balanceOf2;
modifies balanceOf_TokenERC20;
modifies totalSupply_TokenERC20;
procedure {:inline 1} burn~uint256_TokenERC20__fail(this: Ref, msgsender_MSG: Ref, msgvalue_MSG: int, _value_s478: int) returns (success_s478: bool);
modifies revert;
modifies __tmp__sum_balanceOf2;
modifies __tmp__balanceOf_TokenERC20;
modifies __tmp__totalSupply_TokenERC20;
procedure {:inline 1} burnFrom~address~uint256_TokenERC20__success(this: Ref, msgsender_MSG: Ref, msgvalue_MSG: int, _from_s533: Ref, _value_s533: int) returns (success_s533: bool);
modifies revert;
modifies sum_balanceOf2;
modifies balanceOf_TokenERC20;
modifies sum_allowance3;
modifies allowance_TokenERC20;
modifies totalSupply_TokenERC20;
procedure {:inline 1} burnFrom~address~uint256_TokenERC20__fail(this: Ref, msgsender_MSG: Ref, msgvalue_MSG: int, _from_s533: Ref, _value_s533: int) returns (success_s533: bool);
modifies revert;
modifies __tmp__sum_balanceOf2;
modifies __tmp__balanceOf_TokenERC20;
modifies __tmp__sum_allowance3;
modifies __tmp__allowance_TokenERC20;
modifies __tmp__totalSupply_TokenERC20;
procedure {:inline 1} totalSupply_TokenERC20__success(this: Ref, msgsender_MSG: Ref, msgvalue_MSG: int) returns (__ret_0_: int);
procedure {:inline 1} totalSupply_TokenERC20__fail(this: Ref, msgsender_MSG: Ref, msgvalue_MSG: int) returns (__ret_0_: int);
procedure {:inline 1} balanceOf~address_TokenERC20__success(this: Ref, msgsender_MSG: Ref, msgvalue_MSG: int, arg536_s0: Ref) returns (__ret_0_: int);
procedure {:inline 1} balanceOf~address_TokenERC20__fail(this: Ref, msgsender_MSG: Ref, msgvalue_MSG: int, arg536_s0: Ref) returns (__ret_0_: int);
procedure {:inline 1} allowance~address~address_TokenERC20__success(this: Ref, msgsender_MSG: Ref, msgvalue_MSG: int, arg537_s0: Ref, arg538_s0: Ref) returns (__ret_0_: int);
procedure {:inline 1} allowance~address~address_TokenERC20__fail(this: Ref, msgsender_MSG: Ref, msgvalue_MSG: int, arg537_s0: Ref, arg538_s0: Ref) returns (__ret_0_: int);
procedure {:inline 1} FallbackDispatch__fail(from: Ref, to: Ref, amount: int);
modifies revert;
modifies gas;
modifies __tmp__sum_balanceOf2;
modifies __tmp__balanceOf_TokenERC20;
modifies __tmp__sum_allowance3;
modifies __tmp__allowance_TokenERC20;
modifies __tmp__totalSupply_TokenERC20;
procedure {:inline 1} Fallback_UnknownType__fail(from: Ref, to: Ref, amount: int);
modifies revert;
modifies gas;
modifies __tmp__sum_balanceOf2;
modifies __tmp__balanceOf_TokenERC20;
modifies __tmp__sum_allowance3;
modifies __tmp__allowance_TokenERC20;
modifies __tmp__totalSupply_TokenERC20;
procedure {:inline 1} send__fail(from: Ref, to: Ref, amount: int) returns (success: bool);
modifies __tmp__Balance;
modifies __tmp__DType;
modifies __tmp__Alloc;
modifies __tmp__balance_ADDR;
modifies __tmp__M_Ref_bool;
modifies __tmp__sum_whitelisted0;
modifies __tmp__sum_admin1;
modifies __tmp__M_Ref_int;
modifies __tmp__sum_balanceOf2;
modifies __tmp__alloc_allowance_TokenERC20_lvl0;
modifies __tmp__M_Ref_Ref;
modifies __tmp__sum_allowance3;
modifies __tmp__Length;
modifies __tmp__now;
modifies __tmp__name_TokenERC20;
modifies __tmp__symbol_TokenERC20;
modifies __tmp__decimals_TokenERC20;
modifies __tmp__owner_TokenERC20;
modifies __tmp__totalSupply_TokenERC20;
modifies __tmp__lockIn_TokenERC20;
modifies __tmp__whitelisted_TokenERC20;
modifies __tmp__admin_TokenERC20;
modifies __tmp__balanceOf_TokenERC20;
modifies __tmp__allowance_TokenERC20;
modifies revert;
modifies gas;
implementation FreshRefGenerator__fail() returns (newRef: Ref)
{
havoc newRef;
assume ((__tmp__Alloc[newRef]) == (false));
__tmp__Alloc[newRef] := true;
assume ((newRef) != (null));
}

implementation FreshRefGenerator__success() returns (newRef: Ref)
{
havoc newRef;
assume ((Alloc[newRef]) == (false));
Alloc[newRef] := true;
assume ((newRef) != (null));
}

implementation _transfer~address~address~uint256_TokenERC20__fail(this: Ref, msgsender_MSG: Ref, msgvalue_MSG: int, _from_s321: Ref, _to_s321: Ref, _value_s321: int)
{
var __var_1: Ref;
var previousBalances_s321: int;
if (__tmp__lockIn_TokenERC20[this]) {
if (!(__tmp__whitelisted_TokenERC20[this][_from_s321])) {
revert := true;
return;
}
}
__var_1 := null;
if (!((_to_s321) != (null))) {
revert := true;
return;
}
if (!((__tmp__balanceOf_TokenERC20[this][_from_s321]) >= (_value_s321))) {
revert := true;
return;
}
if (!(((__tmp__balanceOf_TokenERC20[this][_to_s321]) + (_value_s321)) > (__tmp__balanceOf_TokenERC20[this][_to_s321]))) {
revert := true;
return;
}
previousBalances_s321 := (__tmp__balanceOf_TokenERC20[this][_from_s321]) + (__tmp__balanceOf_TokenERC20[this][_to_s321]);
__tmp__sum_balanceOf2[this] := (__tmp__sum_balanceOf2[this]) - (__tmp__balanceOf_TokenERC20[this][_from_s321]);
__tmp__balanceOf_TokenERC20[this][_from_s321] := (__tmp__balanceOf_TokenERC20[this][_from_s321]) - (_value_s321);
__tmp__sum_balanceOf2[this] := (__tmp__sum_balanceOf2[this]) + (__tmp__balanceOf_TokenERC20[this][_from_s321]);
__tmp__sum_balanceOf2[this] := (__tmp__sum_balanceOf2[this]) - (__tmp__balanceOf_TokenERC20[this][_to_s321]);
__tmp__balanceOf_TokenERC20[this][_to_s321] := (__tmp__balanceOf_TokenERC20[this][_to_s321]) + (_value_s321);
__tmp__sum_balanceOf2[this] := (__tmp__sum_balanceOf2[this]) + (__tmp__balanceOf_TokenERC20[this][_to_s321]);
}

implementation _transfer~address~address~uint256_TokenERC20__success(this: Ref, msgsender_MSG: Ref, msgvalue_MSG: int, _from_s321: Ref, _to_s321: Ref, _value_s321: int)
{
var __var_1: Ref;
var previousBalances_s321: int;
if (lockIn_TokenERC20[this]) {
if (!(whitelisted_TokenERC20[this][_from_s321])) {
revert := true;
return;
}
}
__var_1 := null;
if (!((_to_s321) != (null))) {
revert := true;
return;
}
if (!((balanceOf_TokenERC20[this][_from_s321]) >= (_value_s321))) {
revert := true;
return;
}
if (!(((balanceOf_TokenERC20[this][_to_s321]) + (_value_s321)) > (balanceOf_TokenERC20[this][_to_s321]))) {
revert := true;
return;
}
previousBalances_s321 := (balanceOf_TokenERC20[this][_from_s321]) + (balanceOf_TokenERC20[this][_to_s321]);
sum_balanceOf2[this] := (sum_balanceOf2[this]) - (balanceOf_TokenERC20[this][_from_s321]);
balanceOf_TokenERC20[this][_from_s321] := (balanceOf_TokenERC20[this][_from_s321]) - (_value_s321);
sum_balanceOf2[this] := (sum_balanceOf2[this]) + (balanceOf_TokenERC20[this][_from_s321]);
sum_balanceOf2[this] := (sum_balanceOf2[this]) - (balanceOf_TokenERC20[this][_to_s321]);
balanceOf_TokenERC20[this][_to_s321] := (balanceOf_TokenERC20[this][_to_s321]) + (_value_s321);
sum_balanceOf2[this] := (sum_balanceOf2[this]) + (balanceOf_TokenERC20[this][_to_s321]);
assert {:EventEmitted "Transfer_TokenERC20"} (true);
assert (((balanceOf_TokenERC20[this][_from_s321]) + (balanceOf_TokenERC20[this][_to_s321])) == (previousBalances_s321));
}

implementation transfer~address~uint256_TokenERC20__fail(this: Ref, msgsender_MSG: Ref, msgvalue_MSG: int, _to_s336: Ref, _value_s336: int)
{
call _transfer~address~address~uint256_TokenERC20__fail(this, msgsender_MSG, msgvalue_MSG, msgsender_MSG, _to_s336, _value_s336);
if (revert) {
return;
}
}

implementation transfer~address~uint256_TokenERC20__success(this: Ref, msgsender_MSG: Ref, msgvalue_MSG: int, _to_s336: Ref, _value_s336: int)
{
call _transfer~address~address~uint256_TokenERC20__success(this, msgsender_MSG, msgvalue_MSG, msgsender_MSG, _to_s336, _value_s336);
if (revert) {
return;
}
}

implementation transferFrom~address~address~uint256_TokenERC20__fail(this: Ref, msgsender_MSG: Ref, msgvalue_MSG: int, _from_s376: Ref, _to_s376: Ref, _value_s376: int) returns (success_s376: bool)
{
if (!((_value_s376) <= (__tmp__allowance_TokenERC20[this][_from_s376][msgsender_MSG]))) {
revert := true;
return;
}
__tmp__sum_allowance3[_from_s376] := (__tmp__sum_allowance3[_from_s376]) - (__tmp__allowance_TokenERC20[this][_from_s376][msgsender_MSG]);
__tmp__allowance_TokenERC20[this][_from_s376][msgsender_MSG] := (__tmp__allowance_TokenERC20[this][_from_s376][msgsender_MSG]) - (_value_s376);
__tmp__sum_allowance3[_from_s376] := (__tmp__sum_allowance3[_from_s376]) + (__tmp__allowance_TokenERC20[this][_from_s376][msgsender_MSG]);
call _transfer~address~address~uint256_TokenERC20__fail(this, msgsender_MSG, msgvalue_MSG, _from_s376, _to_s376, _value_s376);
if (revert) {
return;
}
success_s376 := true;
return;
}

implementation transferFrom~address~address~uint256_TokenERC20__success(this: Ref, msgsender_MSG: Ref, msgvalue_MSG: int, _from_s376: Ref, _to_s376: Ref, _value_s376: int) returns (success_s376: bool)
{
if (!((_value_s376) <= (allowance_TokenERC20[this][_from_s376][msgsender_MSG]))) {
revert := true;
return;
}
sum_allowance3[_from_s376] := (sum_allowance3[_from_s376]) - (allowance_TokenERC20[this][_from_s376][msgsender_MSG]);
allowance_TokenERC20[this][_from_s376][msgsender_MSG] := (allowance_TokenERC20[this][_from_s376][msgsender_MSG]) - (_value_s376);
sum_allowance3[_from_s376] := (sum_allowance3[_from_s376]) + (allowance_TokenERC20[this][_from_s376][msgsender_MSG]);
call _transfer~address~address~uint256_TokenERC20__success(this, msgsender_MSG, msgvalue_MSG, _from_s376, _to_s376, _value_s376);
if (revert) {
return;
}
success_s376 := true;
return;
}

implementation approve~address~uint256_TokenERC20__fail(this: Ref, msgsender_MSG: Ref, msgvalue_MSG: int, _spender_s404: Ref, _value_s404: int) returns (success_s404: bool)
{
__tmp__sum_allowance3[msgsender_MSG] := (__tmp__sum_allowance3[msgsender_MSG]) - (__tmp__allowance_TokenERC20[this][msgsender_MSG][_spender_s404]);
__tmp__allowance_TokenERC20[this][msgsender_MSG][_spender_s404] := _value_s404;
__tmp__sum_allowance3[msgsender_MSG] := (__tmp__sum_allowance3[msgsender_MSG]) + (__tmp__allowance_TokenERC20[this][msgsender_MSG][_spender_s404]);
success_s404 := true;
return;
}

implementation approve~address~uint256_TokenERC20__success(this: Ref, msgsender_MSG: Ref, msgvalue_MSG: int, _spender_s404: Ref, _value_s404: int) returns (success_s404: bool)
{
sum_allowance3[msgsender_MSG] := (sum_allowance3[msgsender_MSG]) - (allowance_TokenERC20[this][msgsender_MSG][_spender_s404]);
allowance_TokenERC20[this][msgsender_MSG][_spender_s404] := _value_s404;
sum_allowance3[msgsender_MSG] := (sum_allowance3[msgsender_MSG]) + (allowance_TokenERC20[this][msgsender_MSG][_spender_s404]);
assert {:EventEmitted "Approval_TokenERC20"} (true);
success_s404 := true;
return;
}

implementation burn~uint256_TokenERC20__fail(this: Ref, msgsender_MSG: Ref, msgvalue_MSG: int, _value_s478: int) returns (success_s478: bool)
{
if (!((__tmp__balanceOf_TokenERC20[this][msgsender_MSG]) >= (_value_s478))) {
revert := true;
return;
}
__tmp__sum_balanceOf2[this] := (__tmp__sum_balanceOf2[this]) - (__tmp__balanceOf_TokenERC20[this][msgsender_MSG]);
__tmp__balanceOf_TokenERC20[this][msgsender_MSG] := (__tmp__balanceOf_TokenERC20[this][msgsender_MSG]) - (_value_s478);
__tmp__sum_balanceOf2[this] := (__tmp__sum_balanceOf2[this]) + (__tmp__balanceOf_TokenERC20[this][msgsender_MSG]);
__tmp__totalSupply_TokenERC20[this] := (__tmp__totalSupply_TokenERC20[this]) - (_value_s478);
success_s478 := true;
return;
}

implementation burn~uint256_TokenERC20__success(this: Ref, msgsender_MSG: Ref, msgvalue_MSG: int, _value_s478: int) returns (success_s478: bool)
{
if (!((balanceOf_TokenERC20[this][msgsender_MSG]) >= (_value_s478))) {
revert := true;
return;
}
sum_balanceOf2[this] := (sum_balanceOf2[this]) - (balanceOf_TokenERC20[this][msgsender_MSG]);
balanceOf_TokenERC20[this][msgsender_MSG] := (balanceOf_TokenERC20[this][msgsender_MSG]) - (_value_s478);
sum_balanceOf2[this] := (sum_balanceOf2[this]) + (balanceOf_TokenERC20[this][msgsender_MSG]);
totalSupply_TokenERC20[this] := (totalSupply_TokenERC20[this]) - (_value_s478);
assert {:EventEmitted "Burn_TokenERC20"} (true);
success_s478 := true;
return;
}

implementation burnFrom~address~uint256_TokenERC20__fail(this: Ref, msgsender_MSG: Ref, msgvalue_MSG: int, _from_s533: Ref, _value_s533: int) returns (success_s533: bool)
{
if (!((__tmp__balanceOf_TokenERC20[this][_from_s533]) >= (_value_s533))) {
revert := true;
return;
}
if (!((_value_s533) <= (__tmp__allowance_TokenERC20[this][_from_s533][msgsender_MSG]))) {
revert := true;
return;
}
__tmp__sum_balanceOf2[this] := (__tmp__sum_balanceOf2[this]) - (__tmp__balanceOf_TokenERC20[this][_from_s533]);
__tmp__balanceOf_TokenERC20[this][_from_s533] := (__tmp__balanceOf_TokenERC20[this][_from_s533]) - (_value_s533);
__tmp__sum_balanceOf2[this] := (__tmp__sum_balanceOf2[this]) + (__tmp__balanceOf_TokenERC20[this][_from_s533]);
__tmp__sum_allowance3[_from_s533] := (__tmp__sum_allowance3[_from_s533]) - (__tmp__allowance_TokenERC20[this][_from_s533][msgsender_MSG]);
__tmp__allowance_TokenERC20[this][_from_s533][msgsender_MSG] := (__tmp__allowance_TokenERC20[this][_from_s533][msgsender_MSG]) - (_value_s533);
__tmp__sum_allowance3[_from_s533] := (__tmp__sum_allowance3[_from_s533]) + (__tmp__allowance_TokenERC20[this][_from_s533][msgsender_MSG]);
__tmp__totalSupply_TokenERC20[this] := (__tmp__totalSupply_TokenERC20[this]) - (_value_s533);
success_s533 := true;
return;
}

implementation burnFrom~address~uint256_TokenERC20__success(this: Ref, msgsender_MSG: Ref, msgvalue_MSG: int, _from_s533: Ref, _value_s533: int) returns (success_s533: bool)
{
var blablabla : int;
if (!((balanceOf_TokenERC20[this][_from_s533]) >= (_value_s533))) {
revert := true;
return;
}
if (!((_value_s533) <= (allowance_TokenERC20[this][_from_s533][msgsender_MSG]))) {
revert := true;
return;
}
sum_balanceOf2[this] := (sum_balanceOf2[this]) - (balanceOf_TokenERC20[this][_from_s533]);
balanceOf_TokenERC20[this][_from_s533] := (balanceOf_TokenERC20[this][_from_s533]) - (_value_s533);
sum_balanceOf2[this] := (sum_balanceOf2[this]) + (balanceOf_TokenERC20[this][_from_s533]);
sum_allowance3[_from_s533] := (sum_allowance3[_from_s533]) - (allowance_TokenERC20[this][_from_s533][msgsender_MSG]);
allowance_TokenERC20[this][_from_s533][msgsender_MSG] := (allowance_TokenERC20[this][_from_s533][msgsender_MSG]) - (_value_s533);
sum_allowance3[_from_s533] := (sum_allowance3[_from_s533]) + (allowance_TokenERC20[this][_from_s533][msgsender_MSG]);
totalSupply_TokenERC20[this] := (totalSupply_TokenERC20[this]) - (_value_s533);
assert {:EventEmitted "Burn_TokenERC20"} (true);
blablabla := 5 / _value_s533;
success_s533 := true;

return;
}

implementation totalSupply_TokenERC20__fail(this: Ref, msgsender_MSG: Ref, msgvalue_MSG: int) returns (__ret_0_: int)
{
__ret_0_ := __tmp__totalSupply_TokenERC20[this];
return;
}

implementation totalSupply_TokenERC20__success(this: Ref, msgsender_MSG: Ref, msgvalue_MSG: int) returns (__ret_0_: int)
{
__ret_0_ := totalSupply_TokenERC20[this];
return;
}

implementation balanceOf~address_TokenERC20__fail(this: Ref, msgsender_MSG: Ref, msgvalue_MSG: int, arg536_s0: Ref) returns (__ret_0_: int)
{
__ret_0_ := __tmp__balanceOf_TokenERC20[this][arg536_s0];
return;
}

implementation balanceOf~address_TokenERC20__success(this: Ref, msgsender_MSG: Ref, msgvalue_MSG: int, arg536_s0: Ref) returns (__ret_0_: int)
{
__ret_0_ := balanceOf_TokenERC20[this][arg536_s0];
return;
}

implementation allowance~address~address_TokenERC20__fail(this: Ref, msgsender_MSG: Ref, msgvalue_MSG: int, arg537_s0: Ref, arg538_s0: Ref) returns (__ret_0_: int)
{
__ret_0_ := __tmp__allowance_TokenERC20[this][arg537_s0][arg538_s0];
return;
}

implementation allowance~address~address_TokenERC20__success(this: Ref, msgsender_MSG: Ref, msgvalue_MSG: int, arg537_s0: Ref, arg538_s0: Ref) returns (__ret_0_: int)
{
__ret_0_ := allowance_TokenERC20[this][arg537_s0][arg538_s0];
return;
}

implementation FallbackDispatch__fail(from: Ref, to: Ref, amount: int)
{
if ((__tmp__DType[to]) == (TokenERC20)) {
assume ((amount) == (0));
} else if ((__tmp__DType[to]) == (tokenRecipient)) {
assume ((amount) == (0));
} else {
call Fallback_UnknownType__fail(from, to, amount);
if (revert) {
return;
}
}
}

implementation FallbackDispatch__success(from: Ref, to: Ref, amount: int)
{
if ((DType[to]) == (TokenERC20)) {
assume ((amount) == (0));
} else if ((DType[to]) == (tokenRecipient)) {
assume ((amount) == (0));
} else {
call Fallback_UnknownType__success(from, to, amount);
if (revert) {
return;
}
}
}

implementation Fallback_UnknownType__fail(from: Ref, to: Ref, amount: int)
{
var this: Ref;
var msgsender_MSG: Ref;
var msgvalue_MSG: int;
var choice: int;
var __ret_0_name: int;
var __ret_0_symbol: int;
var __ret_0_decimals: int;
var __ret_0_owner: Ref;
var __ret_0_totalSupply: int;
var __ret_0_lockIn: bool;
var arg536_s0: Ref;
var __ret_0_balanceOf: int;
var arg537_s0: Ref;
var arg538_s0: Ref;
var __ret_0_allowance: int;
var initialSupply_s130: int;
var tokenName_s130: int;
var tokenSymbol_s130: int;
var crowdsaleOwner_s130: Ref;
var newAddress_s165: Ref;
var oldaddress_s190: Ref;
var newAddress_s209: Ref;
var oldAddress_s234: Ref;
var _to_s336: Ref;
var _value_s336: int;
var _from_s376: Ref;
var _to_s376: Ref;
var _value_s376: int;
var success_s376: bool;
var _spender_s404: Ref;
var _value_s404: int;
var success_s404: bool;
var _spender_s442: Ref;
var _value_s442: int;
var _extraData_s442: int;
var success_s442: bool;
var _value_s478: int;
var success_s478: bool;
var _from_s533: Ref;
var _value_s533: int;
var success_s533: bool;
if ((choice) == (0)) {
revert := true;
return;
}
if ((gas) < (21000)) {
return;
}
if ((__tmp__DType[from]) == (TokenERC20)) {
if ((choice) == (8)) {
gas := (gas) - (21000);
if ((gas) >= (0)) {
assume ((msgvalue_MSG) == (0));
call __ret_0_totalSupply := totalSupply_TokenERC20__fail(from, to, msgvalue_MSG);
if (revert) {
return;
}
}
} else if ((choice) == (7)) {
gas := (gas) - (21000);
if ((gas) >= (0)) {
assume ((msgvalue_MSG) == (0));
call __ret_0_balanceOf := balanceOf~address_TokenERC20__fail(from, to, msgvalue_MSG, arg536_s0);
if (revert) {
return;
}
}
} else if ((choice) == (6)) {
gas := (gas) - (21000);
if ((gas) >= (0)) {
assume ((msgvalue_MSG) == (0));
call __ret_0_allowance := allowance~address~address_TokenERC20__fail(from, to, msgvalue_MSG, arg537_s0, arg538_s0);
if (revert) {
return;
}
}
} else if ((choice) == (5)) {
gas := (gas) - (21000);
if ((gas) >= (0)) {
assume ((msgvalue_MSG) == (0));
call transfer~address~uint256_TokenERC20__fail(from, to, msgvalue_MSG, _to_s336, _value_s336);
if (revert) {
return;
}
}
} else if ((choice) == (4)) {
gas := (gas) - (21000);
if ((gas) >= (0)) {
assume ((msgvalue_MSG) == (0));
call success_s376 := transferFrom~address~address~uint256_TokenERC20__fail(from, to, msgvalue_MSG, _from_s376, _to_s376, _value_s376);
if (revert) {
return;
}
}
} else if ((choice) == (3)) {
gas := (gas) - (21000);
if ((gas) >= (0)) {
assume ((msgvalue_MSG) == (0));
call success_s404 := approve~address~uint256_TokenERC20__fail(from, to, msgvalue_MSG, _spender_s404, _value_s404);
if (revert) {
return;
}
}
} else if ((choice) == (2)) {
gas := (gas) - (21000);
if ((gas) >= (0)) {
assume ((msgvalue_MSG) == (0));
call success_s478 := burn~uint256_TokenERC20__fail(from, to, msgvalue_MSG, _value_s478);
if (revert) {
return;
}
}
} else if ((choice) == (1)) {
gas := (gas) - (21000);
if ((gas) >= (0)) {
assume ((msgvalue_MSG) == (0));
call success_s533 := burnFrom~address~uint256_TokenERC20__fail(from, to, msgvalue_MSG, _from_s533, _value_s533);
if (revert) {
return;
}
}
}
}
}

implementation Fallback_UnknownType__success(from: Ref, to: Ref, amount: int)
{
var this: Ref;
var msgsender_MSG: Ref;
var msgvalue_MSG: int;
var choice: int;
var __ret_0_name: int;
var __ret_0_symbol: int;
var __ret_0_decimals: int;
var __ret_0_owner: Ref;
var __ret_0_totalSupply: int;
var __ret_0_lockIn: bool;
var arg536_s0: Ref;
var __ret_0_balanceOf: int;
var arg537_s0: Ref;
var arg538_s0: Ref;
var __ret_0_allowance: int;
var initialSupply_s130: int;
var tokenName_s130: int;
var tokenSymbol_s130: int;
var crowdsaleOwner_s130: Ref;
var newAddress_s165: Ref;
var oldaddress_s190: Ref;
var newAddress_s209: Ref;
var oldAddress_s234: Ref;
var _to_s336: Ref;
var _value_s336: int;
var _from_s376: Ref;
var _to_s376: Ref;
var _value_s376: int;
var success_s376: bool;
var _spender_s404: Ref;
var _value_s404: int;
var success_s404: bool;
var _spender_s442: Ref;
var _value_s442: int;
var _extraData_s442: int;
var success_s442: bool;
var _value_s478: int;
var success_s478: bool;
var _from_s533: Ref;
var _value_s533: int;
var success_s533: bool;
if ((choice) == (0)) {
revert := true;
return;
}
if ((gas) < (21000)) {
return;
}
if ((DType[from]) == (TokenERC20)) {
if ((choice) == (8)) {
gas := (gas) - (21000);
if ((gas) >= (0)) {
assume ((msgvalue_MSG) == (0));
call __ret_0_totalSupply := totalSupply_TokenERC20__success(from, to, msgvalue_MSG);
if (revert) {
return;
}
}
} else if ((choice) == (7)) {
gas := (gas) - (21000);
if ((gas) >= (0)) {
assume ((msgvalue_MSG) == (0));
call __ret_0_balanceOf := balanceOf~address_TokenERC20__success(from, to, msgvalue_MSG, arg536_s0);
if (revert) {
return;
}
}
} else if ((choice) == (6)) {
gas := (gas) - (21000);
if ((gas) >= (0)) {
assume ((msgvalue_MSG) == (0));
call __ret_0_allowance := allowance~address~address_TokenERC20__success(from, to, msgvalue_MSG, arg537_s0, arg538_s0);
if (revert) {
return;
}
}
} else if ((choice) == (5)) {
gas := (gas) - (21000);
if ((gas) >= (0)) {
assume ((msgvalue_MSG) == (0));
call transfer~address~uint256_TokenERC20__success(from, to, msgvalue_MSG, _to_s336, _value_s336);
if (revert) {
return;
}
}
} else if ((choice) == (4)) {
gas := (gas) - (21000);
if ((gas) >= (0)) {
assume ((msgvalue_MSG) == (0));
call success_s376 := transferFrom~address~address~uint256_TokenERC20__success(from, to, msgvalue_MSG, _from_s376, _to_s376, _value_s376);
if (revert) {
return;
}
}
} else if ((choice) == (3)) {
gas := (gas) - (21000);
if ((gas) >= (0)) {
assume ((msgvalue_MSG) == (0));
call success_s404 := approve~address~uint256_TokenERC20__success(from, to, msgvalue_MSG, _spender_s404, _value_s404);
if (revert) {
return;
}
}
} else if ((choice) == (2)) {
gas := (gas) - (21000);
if ((gas) >= (0)) {
assume ((msgvalue_MSG) == (0));
call success_s478 := burn~uint256_TokenERC20__success(from, to, msgvalue_MSG, _value_s478);
if (revert) {
return;
}
}
} else if ((choice) == (1)) {
gas := (gas) - (21000);
if ((gas) >= (0)) {
assume ((msgvalue_MSG) == (0));
call success_s533 := burnFrom~address~uint256_TokenERC20__success(from, to, msgvalue_MSG, _from_s533, _value_s533);
if (revert) {
return;
}
}
}
}
}

implementation send__fail(from: Ref, to: Ref, amount: int) returns (success: bool)
{
var __exception: bool;
var __snap___tmp__Balance: [Ref]int;
var __snap___tmp__DType: [Ref]ContractName;
var __snap___tmp__Alloc: [Ref]bool;
var __snap___tmp__balance_ADDR: [Ref]int;
var __snap___tmp__M_Ref_bool: [Ref][Ref]bool;
var __snap___tmp__sum_whitelisted0: [Ref]int;
var __snap___tmp__sum_admin1: [Ref]int;
var __snap___tmp__M_Ref_int: [Ref][Ref]int;
var __snap___tmp__sum_balanceOf2: [Ref]int;
var __snap___tmp__alloc_allowance_TokenERC20_lvl0: [Ref][Ref]bool;
var __snap___tmp__M_Ref_Ref: [Ref][Ref]Ref;
var __snap___tmp__sum_allowance3: [Ref]int;
var __snap___tmp__Length: [Ref]int;
var __snap___tmp__now: int;
var __snap___tmp__name_TokenERC20: [Ref]int;
var __snap___tmp__symbol_TokenERC20: [Ref]int;
var __snap___tmp__decimals_TokenERC20: [Ref]int;
var __snap___tmp__owner_TokenERC20: [Ref]Ref;
var __snap___tmp__totalSupply_TokenERC20: [Ref]int;
var __snap___tmp__lockIn_TokenERC20: [Ref]bool;
var __snap___tmp__whitelisted_TokenERC20: [Ref][Ref]bool;
var __snap___tmp__admin_TokenERC20: [Ref][Ref]bool;
var __snap___tmp__balanceOf_TokenERC20: [Ref][Ref]int;
var __snap___tmp__allowance_TokenERC20: [Ref][Ref][Ref]int;
havoc __exception;
if (__exception) {
__snap___tmp__Balance := __tmp__Balance;
__snap___tmp__DType := __tmp__DType;
__snap___tmp__Alloc := __tmp__Alloc;
__snap___tmp__balance_ADDR := __tmp__balance_ADDR;
__snap___tmp__M_Ref_bool := __tmp__M_Ref_bool;
__snap___tmp__sum_whitelisted0 := __tmp__sum_whitelisted0;
__snap___tmp__sum_admin1 := __tmp__sum_admin1;
__snap___tmp__M_Ref_int := __tmp__M_Ref_int;
__snap___tmp__sum_balanceOf2 := __tmp__sum_balanceOf2;
__snap___tmp__alloc_allowance_TokenERC20_lvl0 := __tmp__alloc_allowance_TokenERC20_lvl0;
__snap___tmp__M_Ref_Ref := __tmp__M_Ref_Ref;
__snap___tmp__sum_allowance3 := __tmp__sum_allowance3;
__snap___tmp__Length := __tmp__Length;
__snap___tmp__now := __tmp__now;
__snap___tmp__name_TokenERC20 := __tmp__name_TokenERC20;
__snap___tmp__symbol_TokenERC20 := __tmp__symbol_TokenERC20;
__snap___tmp__decimals_TokenERC20 := __tmp__decimals_TokenERC20;
__snap___tmp__owner_TokenERC20 := __tmp__owner_TokenERC20;
__snap___tmp__totalSupply_TokenERC20 := __tmp__totalSupply_TokenERC20;
__snap___tmp__lockIn_TokenERC20 := __tmp__lockIn_TokenERC20;
__snap___tmp__whitelisted_TokenERC20 := __tmp__whitelisted_TokenERC20;
__snap___tmp__admin_TokenERC20 := __tmp__admin_TokenERC20;
__snap___tmp__balanceOf_TokenERC20 := __tmp__balanceOf_TokenERC20;
__snap___tmp__allowance_TokenERC20 := __tmp__allowance_TokenERC20;
if ((__tmp__Balance[from]) >= (amount)) {
// ---- Logic for payable function START 
__tmp__Balance[from] := (__tmp__Balance[from]) - (amount);
__tmp__Balance[to] := (__tmp__Balance[to]) + (amount);
// ---- Logic for payable function END 
call FallbackDispatch__fail(from, to, amount);
}
success := false;
assume ((revert) || ((gas) < (0)));
__tmp__Balance := __snap___tmp__Balance;
__tmp__DType := __snap___tmp__DType;
__tmp__Alloc := __snap___tmp__Alloc;
__tmp__balance_ADDR := __snap___tmp__balance_ADDR;
__tmp__M_Ref_bool := __snap___tmp__M_Ref_bool;
__tmp__sum_whitelisted0 := __snap___tmp__sum_whitelisted0;
__tmp__sum_admin1 := __snap___tmp__sum_admin1;
__tmp__M_Ref_int := __snap___tmp__M_Ref_int;
__tmp__sum_balanceOf2 := __snap___tmp__sum_balanceOf2;
__tmp__alloc_allowance_TokenERC20_lvl0 := __snap___tmp__alloc_allowance_TokenERC20_lvl0;
__tmp__M_Ref_Ref := __snap___tmp__M_Ref_Ref;
__tmp__sum_allowance3 := __snap___tmp__sum_allowance3;
__tmp__Length := __snap___tmp__Length;
__tmp__now := __snap___tmp__now;
__tmp__name_TokenERC20 := __snap___tmp__name_TokenERC20;
__tmp__symbol_TokenERC20 := __snap___tmp__symbol_TokenERC20;
__tmp__decimals_TokenERC20 := __snap___tmp__decimals_TokenERC20;
__tmp__owner_TokenERC20 := __snap___tmp__owner_TokenERC20;
__tmp__totalSupply_TokenERC20 := __snap___tmp__totalSupply_TokenERC20;
__tmp__lockIn_TokenERC20 := __snap___tmp__lockIn_TokenERC20;
__tmp__whitelisted_TokenERC20 := __snap___tmp__whitelisted_TokenERC20;
__tmp__admin_TokenERC20 := __snap___tmp__admin_TokenERC20;
__tmp__balanceOf_TokenERC20 := __snap___tmp__balanceOf_TokenERC20;
__tmp__allowance_TokenERC20 := __snap___tmp__allowance_TokenERC20;
revert := false;
} else {
if ((__tmp__Balance[from]) >= (amount)) {
// ---- Logic for payable function START 
__tmp__Balance[from] := (__tmp__Balance[from]) - (amount);
__tmp__Balance[to] := (__tmp__Balance[to]) + (amount);
// ---- Logic for payable function END 
call FallbackDispatch__fail(from, to, amount);
success := true;
} else {
success := false;
}
assume ((!(revert)) && ((gas) >= (0)));
}
}

implementation send__success(from: Ref, to: Ref, amount: int) returns (success: bool)
{
var __exception: bool;
havoc __exception;
if (__exception) {
__tmp__Balance := Balance;
__tmp__DType := DType;
__tmp__Alloc := Alloc;
__tmp__balance_ADDR := balance_ADDR;
__tmp__M_Ref_bool := M_Ref_bool;
__tmp__sum_whitelisted0 := sum_whitelisted0;
__tmp__sum_admin1 := sum_admin1;
__tmp__M_Ref_int := M_Ref_int;
__tmp__sum_balanceOf2 := sum_balanceOf2;
__tmp__alloc_allowance_TokenERC20_lvl0 := alloc_allowance_TokenERC20_lvl0;
__tmp__M_Ref_Ref := M_Ref_Ref;
__tmp__sum_allowance3 := sum_allowance3;
__tmp__Length := Length;
__tmp__now := now;
__tmp__name_TokenERC20 := name_TokenERC20;
__tmp__symbol_TokenERC20 := symbol_TokenERC20;
__tmp__decimals_TokenERC20 := decimals_TokenERC20;
__tmp__owner_TokenERC20 := owner_TokenERC20;
__tmp__totalSupply_TokenERC20 := totalSupply_TokenERC20;
__tmp__lockIn_TokenERC20 := lockIn_TokenERC20;
__tmp__whitelisted_TokenERC20 := whitelisted_TokenERC20;
__tmp__admin_TokenERC20 := admin_TokenERC20;
__tmp__balanceOf_TokenERC20 := balanceOf_TokenERC20;
__tmp__allowance_TokenERC20 := allowance_TokenERC20;
if ((__tmp__Balance[from]) >= (amount)) {
// ---- Logic for payable function START 
__tmp__Balance[from] := (__tmp__Balance[from]) - (amount);
__tmp__Balance[to] := (__tmp__Balance[to]) + (amount);
// ---- Logic for payable function END 
call FallbackDispatch__fail(from, to, amount);
}
success := false;
assume ((revert) || ((gas) < (0)));
revert := false;
} else {
if ((Balance[from]) >= (amount)) {
// ---- Logic for payable function START 
Balance[from] := (Balance[from]) - (amount);
Balance[to] := (Balance[to]) + (amount);
// ---- Logic for payable function END 
call FallbackDispatch__success(from, to, amount);
success := true;
} else {
success := false;
}
assume ((!(revert)) && ((gas) >= (0)));
}
}

implementation CorralChoice_tokenRecipient(this: Ref)
{
var msgsender_MSG: Ref;
var msgvalue_MSG: int;
var choice: int;
var tmpNow: int;
havoc msgsender_MSG;
havoc msgvalue_MSG;
havoc choice;
havoc tmpNow;
havoc gas;
assume (((gas) > (4000000)) && ((gas) <= (8000000)));
tmpNow := now;
havoc now;
assume ((now) > (tmpNow));
assume ((msgsender_MSG) != (null));
}

implementation CorralEntry_tokenRecipient()
{
var this: Ref;
var msgsender_MSG: Ref;
var msgvalue_MSG: int;
assume ((null) == (0));
call this := FreshRefGenerator__success();
assume ((now) >= (0));
assume ((DType[this]) == (tokenRecipient));
assume ((!(revert)) && ((gas) >= (0)));
call CorralChoice_tokenRecipient(this);
while (true)
{
}
}

implementation CorralChoice_TokenERC20(this: Ref)
{
var msgsender_MSG: Ref;
var msgvalue_MSG: int;
var choice: int;
var __ret_0_name: int;
var __ret_0_symbol: int;
var __ret_0_decimals: int;
var __ret_0_owner: Ref;
var __ret_0_totalSupply: int;
var __ret_0_lockIn: bool;
var arg536_s0: Ref;
var __ret_0_balanceOf: int;
var arg537_s0: Ref;
var arg538_s0: Ref;
var __ret_0_allowance: int;
var initialSupply_s130: int;
var tokenName_s130: int;
var tokenSymbol_s130: int;
var crowdsaleOwner_s130: Ref;
var newAddress_s165: Ref;
var oldaddress_s190: Ref;
var newAddress_s209: Ref;
var oldAddress_s234: Ref;
var _to_s336: Ref;
var _value_s336: int;
var _from_s376: Ref;
var _to_s376: Ref;
var _value_s376: int;
var success_s376: bool;
var _spender_s404: Ref;
var _value_s404: int;
var success_s404: bool;
var _spender_s442: Ref;
var _value_s442: int;
var _extraData_s442: int;
var success_s442: bool;
var _value_s478: int;
var success_s478: bool;
var _from_s533: Ref;
var _value_s533: int;
var success_s533: bool;
var tmpNow: int;
havoc msgsender_MSG;
havoc msgvalue_MSG;
havoc choice;
havoc __ret_0_name;
havoc __ret_0_symbol;
havoc __ret_0_decimals;
havoc __ret_0_owner;
havoc __ret_0_totalSupply;
havoc __ret_0_lockIn;
havoc arg536_s0;
havoc __ret_0_balanceOf;
havoc arg537_s0;
havoc arg538_s0;
havoc __ret_0_allowance;
havoc initialSupply_s130;
havoc tokenName_s130;
havoc tokenSymbol_s130;
havoc crowdsaleOwner_s130;
havoc newAddress_s165;
havoc oldaddress_s190;
havoc newAddress_s209;
havoc oldAddress_s234;
havoc _to_s336;
havoc _value_s336;
havoc _from_s376;
havoc _to_s376;
havoc _value_s376;
havoc success_s376;
havoc _spender_s404;
havoc _value_s404;
havoc success_s404;
havoc _spender_s442;
havoc _value_s442;
havoc _extraData_s442;
havoc success_s442;
havoc _value_s478;
havoc success_s478;
havoc _from_s533;
havoc _value_s533;
havoc success_s533;
havoc tmpNow;
havoc gas;
assume (((gas) > (4000000)) && ((gas) <= (8000000)));
tmpNow := now;
havoc now;
assume ((now) > (tmpNow));
assume ((msgsender_MSG) != (null));
if ((choice) == (8)) {
gas := (gas) - (21000);
if ((gas) >= (0)) {
assume ((msgvalue_MSG) == (0));
call __ret_0_totalSupply := totalSupply_TokenERC20(this, msgsender_MSG, msgvalue_MSG);
}
} else if ((choice) == (7)) {
gas := (gas) - (21000);
if ((gas) >= (0)) {
assume ((msgvalue_MSG) == (0));
call __ret_0_balanceOf := balanceOf~address_TokenERC20(this, msgsender_MSG, msgvalue_MSG, arg536_s0);
}
} else if ((choice) == (6)) {
gas := (gas) - (21000);
if ((gas) >= (0)) {
assume ((msgvalue_MSG) == (0));
call __ret_0_allowance := allowance~address~address_TokenERC20(this, msgsender_MSG, msgvalue_MSG, arg537_s0, arg538_s0);
}
} else if ((choice) == (5)) {
gas := (gas) - (21000);
if ((gas) >= (0)) {
assume ((msgvalue_MSG) == (0));
call transfer~address~uint256_TokenERC20(this, msgsender_MSG, msgvalue_MSG, _to_s336, _value_s336);
}
} else if ((choice) == (4)) {
gas := (gas) - (21000);
if ((gas) >= (0)) {
assume ((msgvalue_MSG) == (0));
call success_s376 := transferFrom~address~address~uint256_TokenERC20(this, msgsender_MSG, msgvalue_MSG, _from_s376, _to_s376, _value_s376);
}
} else if ((choice) == (3)) {
gas := (gas) - (21000);
if ((gas) >= (0)) {
assume ((msgvalue_MSG) == (0));
call success_s404 := approve~address~uint256_TokenERC20(this, msgsender_MSG, msgvalue_MSG, _spender_s404, _value_s404);
}
} else if ((choice) == (2)) {
gas := (gas) - (21000);
if ((gas) >= (0)) {
assume ((msgvalue_MSG) == (0));
call success_s478 := burn~uint256_TokenERC20(this, msgsender_MSG, msgvalue_MSG, _value_s478);
}
} else if ((choice) == (1)) {
gas := (gas) - (21000);
if ((gas) >= (0)) {
assume ((msgvalue_MSG) == (0));
call success_s533 := burnFrom~address~uint256_TokenERC20(this, msgsender_MSG, msgvalue_MSG, _from_s533, _value_s533);
}
}
}



implementation main()
{
var this: Ref;
var msgsender_MSG: Ref;
var msgvalue_MSG: int;
var initialSupply_s130: int;
var tokenName_s130: int;
var tokenSymbol_s130: int;
var crowdsaleOwner_s130: Ref;
assume ((null) == (0));
call this := FreshRefGenerator__success();
assume ((now) >= (0));
assume ((DType[this]) == (TokenERC20));
assume ((!(revert)) && ((gas) >= (0)));
call CorralChoice_TokenERC20(this);
while (true)
{
}
}


