function [ Result ] = analyse( Matrix, shape)
%UNTITLED �˴���ʾ�йش˺�����ժҪ
%   �˴���ʾ��ϸ˵��
n = size(Matrix, 2)
S=Matrix(:,2:n)
Result=PCA(S)
plot(Result(:,1), Result(:,2), shape)
end

